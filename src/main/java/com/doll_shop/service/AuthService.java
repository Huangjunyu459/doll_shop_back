package com.doll_shop.service;

import com.doll_shop.dto.LoginRequest;
import com.doll_shop.dto.LoginResponse;
import com.doll_shop.dto.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    String register(RegisterRequest request);   // 新增
}