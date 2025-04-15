package com.ktg.web.controller.seller;

import cn.hutool.json.JSONUtil;
import com.asinking.com.openapi.sdk.sign.ApiSign;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktg.framework.web.domain.server.Sys;
import com.ktg.web.domain.ActiveItems;
import com.ktg.web.domain.SalesOrder;
import com.ktg.web.domain.SalesOrderSku;
import com.ktg.web.service.ActiveItemsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.ktg.framework.datasource.DynamicDataSourceContextHolder.log;

@RestController
public class AddSkuController {
    private static final String AUTH_URL = "https://openapi.lingxing.com/api/auth-server/oauth/access-token";
    private static final String ORDER_LIST_URL = "https://openapi.lingxing.com/erp/sc/routing/data/local_inventory/productList";
    private static final String APP_ID = "ak_nbbisNHoOA6Hg";
    private static final String APP_SECRET = "mxDmU4zvHFjvvvPaLJxxDg==";
    @Autowired
    private ActiveItemsService activeItemsService;


    @GetMapping("getAddSku")
    public String getAddSku() {
        try {
            String accessToken = getAccessToken();
            return fetchOrderList(accessToken).toString();
//            return fetchOrderList(accessToken).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject fetchOrderList(String accessToken) throws Exception {
        for (int attempt = 0; attempt < 5; attempt++) {
            long timestampInSeconds = System.currentTimeMillis() / 1000;
            String sign = getSign(accessToken, timestampInSeconds);

            URL url = new URL(ORDER_LIST_URL + "?access_token=" + accessToken + "&sign=" + sign + "&timestamp=" + timestampInSeconds + "&app_key=" + APP_ID);
            HttpURLConnection conn = null;
            try {
                conn = createConnection(url.toString(), "POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject requestBody = new JSONObject();
                // 写入请求体
                conn.getOutputStream().write(requestBody.toString().getBytes("utf-8"));

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String responseContent = getResponseContent(conn);
                    JSONObject jsonResponse = new JSONObject(responseContent);

                    System.out.println("{code}:" + jsonResponse.getInt("code"));
                    Integer code = jsonResponse.getInt("code");
                    if (code.equals(0)) {
                        JSONArray data = jsonResponse.optJSONArray("data");
                        if (data != null) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject order = data.getJSONObject(i);
                                String productName = order.optString("product_name", null);
                                String sku = order.optString("sku", null);

                                ActiveItems activeItems = new ActiveItems();
                                activeItems.setSku(sku);
                                activeItems.setLocalName(productName);
                                activeItems.setCreateTime(getNow());
                                activeItemsService.save(activeItems);
                            }
                        }
                    }
                    return jsonResponse;
                } else {
                    throw new IOException("Failed to fetch order list: HTTP response code " + responseCode);
                }
            } catch (IOException e) {
                System.err.println("Attempt " + (attempt + 1) + " failed: " + e.getMessage());
                if (attempt == 4) {
                    throw e; // 如果到达最大重试次数，抛出异常
                }
            } finally {
                if (conn != null) {
                    conn.disconnect(); // 关闭连接
                }
            }

            Thread.sleep(2000); // 等待 2 秒后重试
        }

        throw new RuntimeException("Failed to fetch order list after 5 attempts: API sign not correct");
    }



    public static String getNow() {
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();

        // 定义格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化当前日期和时间
        String formattedDate = now.format(formatter);

        return formattedDate;

    }


    private String getSign( String accessToken, long timestampInSeconds) throws Exception {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("timestamp", timestampInSeconds);
        queryParam.put("access_token", accessToken);
        queryParam.put("app_key", APP_ID);

        Map<String, Object> body = new HashMap<>();

        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(queryParam);
        signMap.putAll(body);

        String sign = ApiSign.sign(signMap, APP_ID);
        log.info("sign: {}", sign);
        return sign;
    }


    private HttpURLConnection createConnection(String urlString, String method) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        return connection;
    }

    private String getResponseContent(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response.toString();
    }

    private String getAccessToken() throws IOException {
        HttpURLConnection connection = createConnection(AUTH_URL, "POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data");

        // 构建请求体
        String boundary = Long.toHexString(System.currentTimeMillis());
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            out.writeBytes("--" + boundary + "\r\n");
            out.writeBytes("Content-Disposition: form-data; name=\"appId\"\r\n\r\n");
            out.writeBytes(APP_ID + "\r\n");
            out.writeBytes("--" + boundary + "\r\n");
            out.writeBytes("Content-Disposition: form-data; name=\"appSecret\"\r\n\r\n");
            out.writeBytes(APP_SECRET + "\r\n");
            out.writeBytes("--" + boundary + "--\r\n");
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String responseContent = getResponseContent(connection);
            JSONObject jsonResponse = new JSONObject(responseContent);
            return jsonResponse.getJSONObject("data").getString("access_token");
        } else {
            throw new IOException("Failed to get access token: " + responseCode);
        }
    }






}
