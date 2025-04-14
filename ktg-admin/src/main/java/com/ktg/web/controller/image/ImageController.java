package com.ktg.web.controller.image;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageController {

    @PostMapping("down")
    public Map<String, String> down(@RequestParam String url) {
        String imageUrl = url; // 替换为你要下载的图片URL
        // 获取最后一个 '/' 的索引
        int lastSlashIndex = url.lastIndexOf('/');

        // 从最后一个 '/' 的下一个位置开始提取子字符串
        String fileName = url.substring(lastSlashIndex + 1);
        String destinationFile = "D:/Downloads/" + fileName; // 下载后保存的文件名

        Map<String, String> response = new HashMap<>();
        try {
            downloadImage(imageUrl, destinationFile);
            System.out.println("图片下载成功: " + destinationFile);
            response.put("图片下载成功", destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("下载图片失败");
            response.put("错误", "下载图片失败");
        }
        return response; // 返回一个 Map 对象作为 JSON 响应
    }

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestParam("file") MultipartFile file) {
        System.out.println("file:"+file.getOriginalFilename());
        // 检查文件是否为空
        if (file.isEmpty()) {
            return "上传失败，请选择一个文件！";
        }

        // 如果需要，可以添加文件大小和类型的检查

        // 保存文件的路径
        String uploadDir = "D:\\MES\\upload_img"; // 替换为你的上传目录
        File dest = new File(uploadDir + File.separator + file.getOriginalFilename());

        try {
            // 将文件保存到指定路径
            file.transferTo(dest);
            return "上传成功！文件名：" + file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败！请重试。";
        }
    }

    public void downloadImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl); // 创建一个URL对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // 打开连接
        connection.setRequestMethod("GET"); // 设置请求方法为GET
        connection.connect(); // 发起请求

        // 检查响应码
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // 创建输入流来读取图片数据
            try (InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) { // 创建文件输出流

                byte[] buffer = new byte[1024];
                int bytesRead;
                // 将输入流数据写入到输出流
                while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            System.out.println("下载失败，响应码: " + connection.getResponseCode());
        }

        connection.disconnect(); // 断开连接
    }
}
