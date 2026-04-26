package com.doll_shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doll_shop.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}