package com.doll_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doll_shop.entity.Product;
import com.doll_shop.entity.ProductLike;
import com.doll_shop.mapper.ProductLikeMapper;
import com.doll_shop.mapper.ProductMapper;
import com.doll_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final ProductLikeMapper productLikeMapper;

    @Override
    @Transactional
    public void addViewCount(Long id) {
        baseMapper.increaseViewCount(id);
    }

    @Override
    @Transactional
    public void addLike(Long productId, String guestId) {
        // 1. 根据 游客ID 和 商品ID 查询记录
        Long count = productLikeMapper.selectCount(new LambdaQueryWrapper<ProductLike>()
                .eq(ProductLike::getGuestId, guestId) // 改为 getGuestId
                .eq(ProductLike::getProductId, productId));

        // 2. 如果没点过赞，插入记录并加 1
        if (count == 0) {
            ProductLike like = new ProductLike();
            like.setGuestId(guestId); // 存入游客ID
            like.setProductId(productId);
            productLikeMapper.insert(like);

            baseMapper.increaseLikeCount(productId);
        }
    }
}