package com.ktg.web.controller.seller;

import cn.hutool.json.JSONUtil;
import com.asinking.com.openapi.sdk.sign.ApiSign;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktg.framework.web.domain.server.Sys;
import com.ktg.web.domain.SalesOrder;
import com.ktg.web.domain.SalesOrderSku;
import com.ktg.web.service.SalesOrderService;
import com.ktg.web.service.SalesOrderSkuService;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.ktg.framework.datasource.DynamicDataSourceContextHolder.log;

@RestController
public class SellerController {

    private static final String AUTH_URL = "https://openapi.lingxing.com/api/auth-server/oauth/access-token";
    private static final String ORDER_LIST_URL = "https://openapi.lingxing.com/pb/mp/order/v2/list";
    private static final String APP_ID = "ak_nbbisNHoOA6Hg";
    private static final String APP_SECRET = "mxDmU4zvHFjvvvPaLJxxDg==";
    private final SalesOrderService salesOrderService;
    private final SalesOrderSkuService salesOrderSkuService;

    public SellerController(SalesOrderService salesOrderService, SalesOrderSkuService salesOrderSkuService) {
        this.salesOrderService = salesOrderService;
        this.salesOrderSkuService = salesOrderSkuService;
    }

    @GetMapping("getSellers")
    public String getSellers() {
        try {
            String accessToken = getAccessToken();
            return fetchOrderList(accessToken).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    private JSONObject fetchOrderList(String accessToken) throws Exception {
        // 尝试重试最多5次
        for (int attempt = 0; attempt < 5; attempt++) {
            long timestampInSeconds = System.currentTimeMillis() / 1000;
            long startTime = getStartTime();
            long endTime = timestampInSeconds;
            Integer order_status = 9;

            // 创建请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("start_time", startTime);
            requestBody.put("end_time", endTime);
            requestBody.put("date_type", "update_time");
            requestBody.put("offset", 0);
            requestBody.put("length", 500);
            requestBody.put("order_status", order_status);
            requestBody.put("platform_code", Arrays.asList(10002));


            System.out.println("已重试："+attempt);
            // 获取签名
            String sign = getSign(requestBody, accessToken, timestampInSeconds);

            // 发送请求
            URL url = new URL(ORDER_LIST_URL + "?access_token=" + accessToken + "&sign=" + sign + "&timestamp=" + timestampInSeconds + "&app_key=" + APP_ID);
            HttpURLConnection conn = createConnection(url.toString(), "POST");
            conn.setRequestProperty("Content-Type", "application/json");

            // 写入请求体
            conn.getOutputStream().write(requestBody.toString().getBytes("utf-8"));

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseContent = getResponseContent(conn);
                JSONObject jsonResponse = new JSONObject(responseContent);
                System.out.println("{code}:"+jsonResponse.getInt("code"));
                Integer code = jsonResponse.getInt("code");

                // 检查响应中的 msg 字段
                if (code.equals(0)) {
                    JSONObject data = (JSONObject) jsonResponse.get("data");
                    JSONArray list = data.getJSONArray("list");
//                    System.out.println("jsonResponse.get(\"list\"):"+ list);
                    // 将字符串转换为 JSONArray
                    JSONArray jsonArray = new JSONArray(list);

                    // 遍历 JSON 数组
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject order = jsonArray.getJSONObject(i);
                        // 获取 global_order_no 的值
                        String globalOrderNo = order.getString("global_order_no");
                        System.out.println("Global Order No: " + globalOrderNo);
                        // 获取 remark 的值
                        String remark = order.optString("remark");

                        // 获取 platform_info 数组
                        JSONArray platformInfoArray = order.getJSONArray("platform_info");
                        String platformOrderName = "";

                        // 创建一个 List 来存储 platformOrderName
                        List<String> platformOrderNames = new ArrayList<>();
                        for (int j = 0; j < platformInfoArray.length(); j++) {
                            JSONObject platformInfo = platformInfoArray.getJSONObject(j);
                            // 获取 platform_order_name 的值
                            platformOrderName = platformInfo.getString("platform_order_name");
                            platformOrderNames.add(platformOrderName);
                            System.out.println("Platform Order Name: " + platformOrderName);
                        }
                        // 将 List 转换为 String，使用逗号分隔
                        platformOrderName = String.join(", ", platformOrderNames);
                        // 获取时间戳并转换为毫秒（Java 中的时间戳是以毫秒为单位）
                        Integer timestamp = order.optInt("global_payment_time");
                        System.out.println("Global Payment Time: " + timestamp);
                        // 将时间戳转换为毫秒
                        long timestampInMillis = timestamp * 1000L; // 转换为毫秒

                        // 创建日期对象
                        Date date = new Date(timestampInMillis);
                        // 计算三天后的时间戳（毫秒）
                        long threeDaysInMillis = 3 * 24 * 60 * 60 * 1000; // 三天的毫秒数
                        long futureTimestampInMillis = timestampInMillis + threeDaysInMillis;

                        // 创建日期对象
                        Date futureDate = new Date(futureTimestampInMillis);

                        // 格式化日期
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        sdf.setTimeZone(TimeZone.getDefault()); // 设置时区（可以根据需要进行调整）

                        // 获取格式化后的日期字符串
                        String formattedDate = sdf.format(futureDate);

                        // 输出结果
                        System.out.println("Formatted Date (3 days later): " + formattedDate);

                        SalesOrder salesOrder = new SalesOrder();
                        salesOrder.setGlobalOrderNo(globalOrderNo);
                        salesOrder.setPlatformOrderName(platformOrderName);
                        salesOrder.setRequestStatus(order_status);
                        salesOrder.setIfOutTime(0);
                        salesOrder.setPlanEndTime(formattedDate);
                        salesOrder.setRealEndTime("");
                        salesOrder.setRemark(remark);
                        salesOrder.setPlatformStatus(0);
                        salesOrder.setCreatePerson("admin");
                        salesOrder.setUpdateTime("");
                        salesOrder.setDeleteFlag(0);
                        salesOrder.setCreateTime(getTime());
                        SalesOrder createdSalesOrder = salesOrderService.saveSalesOrder(salesOrder);

                        // 获取 item_info 数组
                        JSONArray itemInfoArray = order.getJSONArray("item_info");
                        for (int j = 0; j < itemInfoArray.length(); j++) {
                            JSONObject itemInfo = itemInfoArray.getJSONObject(j);

                            // 获取 sku 和 msku 的值
                            String sku = itemInfo.optString("local_sku"); // 使用 optString 以防键不存在
                            String msku = itemInfo.optString("msku");
                            Integer quantity = itemInfo.optInt("quantity");
                            String local_product_name = itemInfo.optString("local_product_name");
                            String remark_order = itemInfo.optString("remark");
                            String productionUrl = extractUrl(remark_order, "customily-production-url:");
                            String previewUrl = extractUrl(remark_order, "customily-preview:");

                            SalesOrderSku salesOrderSku = new SalesOrderSku();
                            // 不设置 salesOrderSku.setId();  // 不设置 ID，数据库会自增
                            salesOrderSku.setGlobalOrderNo(globalOrderNo);
                            salesOrderSku.setSku(sku);
                            salesOrderSku.setMsku(msku);
                            salesOrderSku.setCount(quantity);
                            salesOrderSku.setLocalProductName(local_product_name);
                            salesOrderSku.setProductImage(productionUrl);
                            salesOrderSku.setProductPlanCode("");
                            salesOrderSku.setRenderingImage(previewUrl);
                            salesOrderSku.setCraft("");
                            salesOrderSku.setUpdateTime("");
                            salesOrderSku.setDeleteFlag(0);
                            salesOrderSku.setCreateP("system");
//                            salesOrderSku.setRawMaterial("");
//                            salesOrderSku.setPerson("");
                            salesOrderSku.setCreateTime(getTime());

                            try {
                                ObjectMapper objectMapper = new ObjectMapper();
                                // 将对象转换为 JSON 字符串
                                String jsonString = objectMapper.writeValueAsString(salesOrderSku);
                                System.out.println("jsonStringx:"+jsonString);
                                SalesOrderSku createdSalesOrderSku = salesOrderSkuService.saveSalesOrder(salesOrderSku);;
                            }catch (Exception e) {
                                e.printStackTrace(); // 打印异常信息
                            }



                            System.out.println("Customily Production URL: " + productionUrl);
                            System.out.println("Customily Preview URL: " + previewUrl);
                            System.out.println("SKU: " + sku);
                            System.out.println("MSKU: " + msku);
                            System.out.println("quantity: " + quantity);
                            System.out.println("local_product_name: " + local_product_name);
                            System.out.println("remark: " + remark_order);
                        }

                    }
                    return jsonResponse;  // 成功返回数据
                }
            } else {
                throw new IOException("Failed to fetch order list: " + responseCode);
            }

            // 等待一段时间再重试（可选）
            try {
                Thread.sleep(2000); // 等待2秒再重试
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 处理异常
            }
        }

        // 如果重试5次后仍然失败，则抛出异常
        throw new RuntimeException("Failed to fetch order list after 5 attempts: API sign not correct");
    }

    private static String extractUrl(String input, String key) {
        String regex = key + "(.*?)_";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1); // 提取 URL
        }
        return null; // 如果没有找到，返回 null
    }

    public static String getTime(){
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();

        // 格式化为字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        return formattedTime;
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

    private long getStartTime() throws Exception {
        String dateTimeString = "2025-02-05 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    private String getSign(JSONObject jsonob, String accessToken, long timestampInSeconds) throws Exception {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("timestamp", timestampInSeconds);
        queryParam.put("access_token", accessToken);
        queryParam.put("app_key", APP_ID);

        Map<String, Object> body = new HashMap<>();
        body.put("start_time", jsonob.getLong("start_time"));
        body.put("end_time", jsonob.getLong("end_time"));
        body.put("date_type", jsonob.getString("date_type"));
        body.put("offset", jsonob.getInt("offset"));
        body.put("length", jsonob.getInt("length"));
        body.put("order_status", jsonob.getInt("order_status"));
        body.put("platform_code", JSONUtil.toJsonStr(jsonob.get("platform_code")));

        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(queryParam);
        signMap.putAll(body);

        String sign = ApiSign.sign(signMap, APP_ID);
        log.info("sign: {}", sign);
        return sign;
    }

}
