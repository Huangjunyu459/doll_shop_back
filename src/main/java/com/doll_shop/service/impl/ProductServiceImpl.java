package com.doll_shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doll_shop.entity.Product;
import com.doll_shop.mapper.ProductMapper;
import com.doll_shop.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}