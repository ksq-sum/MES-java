package com.ktg.web.controller.seller;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader {

    public static void main(String[] args) {
        String imageUrl = "https://cdn.customily.com/ExportFile/10701a-4/tsf104BASIC-TEENavy2XL_334075_1_1.png"; // 替换为你要下载的图片URL
        String destinationFile = "D:/Downloads/tsf104BASIC-TEENavy2XL_334075_1_1.png";// 下载后保存的文件名

        try {
            downloadImage(imageUrl, destinationFile);
            System.out.println("图片下载成功: " + destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("下载图片失败");
        }
    }

    public static void downloadImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl); // 创建一个URL对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // 打开连接
        connection.setRequestMethod("GET"); // 设置请求方法为GET
        connection.connect(); // 发起请求

        // 检查响应码
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // 创建输入流来读取图片数据
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(destinationFile); // 创建文件输出流

            byte[] buffer = new byte[1024];
            int bytesRead;
            // 将输入流数据写入到输出流
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            // 关闭流
            fileOutputStream.close();
            inputStream.close();
        } else {
            System.out.println("下载失败，响应码: " + connection.getResponseCode());
        }

        connection.disconnect(); // 断开连接
    }
}
