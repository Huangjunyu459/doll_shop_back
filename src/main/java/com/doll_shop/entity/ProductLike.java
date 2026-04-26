package com.doll_shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("product_like")
public class ProductLike {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 修改为 String 类型的 guestId
    private String guestId;

    private Long productId;
    private LocalDateTime createdAt;
}