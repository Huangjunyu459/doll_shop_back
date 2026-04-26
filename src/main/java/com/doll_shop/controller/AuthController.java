package com.doll_shop.controller;

import com.doll_shop.dto.LoginRequest;
import com.doll_shop.dto.LoginResponse;
import com.doll_shop.dto.R;
import com.doll_shop.dto.RegisterRequest;
import com.doll_shop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public R<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return R.ok(response);
    }

    @PostMapping("/register")
    public R<String> register(@RequestBody RegisterRequest request) {
        String result = authService.register(request);
        return R.ok(result);
    }
}