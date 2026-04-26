package com.doll_shop.service.impl;

import com.doll_shop.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    // 读取 application.yml 中的配置，默认存放到项目根目录下的 uploads 文件夹
    @Value("${upload.base-dir:./uploads/}")
    private String baseDir;

    /**
     * 上传图片（直接保存到正式目录）
     */
    @Override
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        // 1. 检查并创建基础保存目录
        Path uploadPath = Paths.get(baseDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("创建上传目录失败", e);
            }
        }

        // 2. 生成安全且唯一的随机文件名 (UUID)
        String originalName = file.getOriginalFilename();
        String extension = (originalName != null && originalName.contains("."))
                ? originalName.substring(originalName.lastIndexOf("."))
                : ".jpg"; // 默认后缀
        String newFileName = UUID.randomUUID() + extension;

        // 3. 将接收到的二进制文件保存到本地硬盘
        Path targetPath = uploadPath.resolve(newFileName);
        try {
            file.transferTo(targetPath.toAbsolutePath().toFile());
        } catch (IOException e) {
            throw new RuntimeException("文件保存到本地失败", e);
        }

        // 4. 返回前端可直接访问的永久网络 URL
        // WebConfig 中已经配置了 /uploads/** 映射到本地目录
        return "/uploads/" + newFileName;
    }

    /**
     * 删除图片（当管理员在后台删除误传的图片时调用）
     */
    @Override
    public boolean deleteTempImage(String url) {
        if (url == null || !url.startsWith("/uploads/")) {
            return false;
        }

        // 提取真正的文件名
        String fileName = url.substring("/uploads/".length());
        Path filePath = Paths.get(baseDir, fileName);

        try {
            // 从硬盘中彻底删除物理文件
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
}