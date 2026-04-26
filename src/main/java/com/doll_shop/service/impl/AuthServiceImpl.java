package com.doll_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.doll_shop.dto.LoginRequest;
import com.doll_shop.dto.LoginResponse;
import com.doll_shop.dto.RegisterRequest;
import com.doll_shop.entity.SysUser;
import com.doll_shop.mapper.SysUserMapper;
import com.doll_shop.service.AuthService;
import com.doll_shop.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        SysUser user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, request.username())
        );

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 严格 BCrypt 匹配
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new RuntimeException("账号已被禁用");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new LoginResponse(token, user.getUsername());
    }

    @Override
    public String register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, request.username())) != null) {
            throw new RuntimeException("用户名已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));  // 加密存储
        user.setNickname(request.nickname() != null ? request.nickname() : request.username());
        user.setEnabled(true);

        sysUserMapper.insert(user);
        return "注册成功，请使用新账号登录";
    }
}