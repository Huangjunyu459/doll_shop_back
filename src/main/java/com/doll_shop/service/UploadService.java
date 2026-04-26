package com.doll_shop.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadImage(MultipartFile file);
    boolean deleteTempImage(String url);
}