package com.doll_shop.dto;

public record RegisterRequest(
        String username,
        String password,
        String nickname
) {}