package com.doll_shop.controller;

import com.doll_shop.dto.R;
import com.doll_shop.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    /**
     * 异步上传图片（新增/编辑商品时调用）
     */
    @PostMapping("/image")
    public R<String> uploadImage(@RequestPart("file") MultipartFile file) {
        try {
            String url = uploadService.uploadImage(file);
            return R.ok("上传成功", url);
        } catch (Exception e) {
            return R.error("图片上传失败: " + e.getMessage());
        }
    }

    /**
     * 删除临时图片（用户取消新增/编辑时调用）
     */
    @DeleteMapping("/image")
    public R<String> deleteTempImage(@RequestParam("url") String url) {
        boolean success = uploadService.deleteTempImage(url);
        if (success) {
            return R.ok("已删除临时图片", null);
        } else {
            return R.error("删除临时图片失败");
        }
    }
}