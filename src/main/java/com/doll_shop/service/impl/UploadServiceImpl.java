package com.doll_shop.service.impl;

import com.doll_shop.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    @Value("${upload.base-dir:./uploads/}")
    private String baseDir;

    @Value("${upload.temp-dir:temp/}")
    private String tempDir;

    /**
     * 上传图片（保存到临时目录）
     */
    @Override
    public String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        // 创建临时目录
        Path tempPath = Paths.get(baseDir, tempDir);
        if (!Files.exists(tempPath)) {
            try {
                Files.createDirectories(tempPath);
            } catch (IOException e) {
                throw new RuntimeException("创建上传目录失败", e);
            }
        }

        // 生成唯一文件名
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + extension;

        Path targetPath = tempPath.resolve(newFileName);

        try {
            file.transferTo(targetPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败", e);
        }

        // 返回可访问的URL（前端直接用这个URL预览）
        return "/uploads/" + tempDir + newFileName;
    }

    /**
     * 删除临时图片（用户取消操作时调用）
     */
    @Override
    public boolean deleteTempImage(String url) {
        if (url == null || !url.startsWith("/uploads/temp/")) {
            return false;
        }

        String relativePath = url.substring(1); // 去掉开头的 /
        Path filePath = Paths.get(baseDir, relativePath);

        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
}