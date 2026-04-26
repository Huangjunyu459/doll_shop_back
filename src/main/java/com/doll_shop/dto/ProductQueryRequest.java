package com.doll_shop.dto;

import java.util.List;

/**
 * 接收前端传来的商品分页多条件筛选参数
 */
public record ProductQueryRequest(
        String name,             // 搜索框输入的名称
        List<String> categories, // 勾选的多个分类，如 ["毛绒玩具", "盲盒"]
        List<String> ips,        // 勾选的多个IP，如 ["迪士尼", "万代"]
        List<String> statuses,   // 勾选的多个状态，如 ["售卖中", "打折"]
        Integer pageNum,         // 当前页码
        Integer pageSize         // 每页条数
) {}