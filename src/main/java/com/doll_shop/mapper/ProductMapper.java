package com.doll_shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doll_shop.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    @Update("UPDATE product SET view_count = view_count + 1 WHERE id = #{id}")
    void increaseViewCount(Long id);

    @Update("UPDATE product SET like_count = like_count + 1 WHERE id = #{id}")
    void increaseLikeCount(Long id);
}