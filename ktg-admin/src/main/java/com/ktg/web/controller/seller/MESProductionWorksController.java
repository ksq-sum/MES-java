package com.ktg.web.controller.seller;

import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.MESProductionWorks;
import com.ktg.web.service.MESProductionOrdersService;
import com.ktg.web.service.MESProductionWorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/productionWorks")
public class MESProductionWorksController {
    @Autowired
    private MESProductionWorksService mesProductionWorksService;
    private static final String BASE_PATH = "D:/img/";

    @PostMapping("createProductionWork")
    public ResponseEntity<String> createProductionOrder(@RequestBody MESProductionWorks mesProductionWorks) {
        return mesProductionWorksService.saveMESProductionWorks(mesProductionWorks);
    }

    @PostMapping("/updateorderWork")
    public int updateorderWork(@RequestBody Map<String, Object> map) {

        return mesProductionWorksService.updateorderWork((List<String>)map.get("ids"),(String)map.get("code"));
    }

    //备注修改
    @PostMapping("updateremackWork")
    public int updateremackWork(int id,String remack) {
        return mesProductionWorksService.updateremackWork(id,remack);
    }

    //图片修改
    @PostMapping("updateImg")
    public int updateImg(Integer id,String img) throws IOException {
        return mesProductionWorksService.updateImg(id,img);
    }

    @PostMapping("img1")
    public String img1(@RequestParam("image") MultipartFile image) throws IOException {
        try {
            // 创建上传目录（如果不存在）
            File directory = new File("D:\\img");
            if (!directory.exists()) {
                //创建
                directory.mkdirs();
            }
            // 生成唯一的文件名，避免文件名冲突
            String originalFilename = image.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            // 构建保存文件的完整路径
            Path filePath = Paths.get("D:\\img", uniqueFileName);
            // 将文件写入指定路径
            Files.write(filePath, image.getBytes());
            //返回文件名
            return uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("one")
    public ResponseEntity<Resource> one(String o) {
        try {
            // 拼接完整路径并校验安全性（防止路径穿越攻击）
            String fullPath = BASE_PATH + o;
            File file = new File(fullPath);
            if (!file.exists() || file.isDirectory()) {
                return ResponseEntity.notFound().build();
            }
            // 返回文件流
            Resource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
            MediaType mediaType = getMediaType(file.getName());
            return ResponseEntity.ok()
                    .contentType(mediaType) // 根据文件类型调整
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    private MediaType getMediaType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (fileName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (fileName.endsWith(".txt")) {
            return MediaType.TEXT_PLAIN;
        }
        // 对于其他未知类型，返回默认的二进制流类型
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
