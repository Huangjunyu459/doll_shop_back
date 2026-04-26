package com.doll_shop.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName(value = "product", autoResultMap = true)
public class Product {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private String image;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> category;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> ip;

    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
    private Integer quantity;
    private String status;   // 售卖中 / 已售 / 私人珍藏
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}