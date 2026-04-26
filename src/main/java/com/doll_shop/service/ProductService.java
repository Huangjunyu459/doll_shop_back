package com.doll_shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doll_shop.entity.Product;

public interface ProductService extends IService<Product> {
    void addViewCount(Long id);
    void addLike(Long productId, String guestId);
}