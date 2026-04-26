package com.doll_shop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doll_shop.dto.ProductQueryRequest;
import com.doll_shop.dto.R;
import com.doll_shop.entity.Product;
import com.doll_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 前端展示页：多条件分页查询（支持同组多选 OR，跨组 AND）
     * 请求示例: GET /api/products/page?name=玩偶&categories=毛绒玩具&ips=迪士尼&ips=不二家&pageNum=1&pageSize=10
     */
    @GetMapping("/page")
    public R<IPage<Product>> getPage(ProductQueryRequest query) {
        // 1. 构造分页对象（默认第1页，10条记录）
        Page<Product> page = new Page<>(
                query.pageNum() == null ? 1 : query.pageNum(),
                query.pageSize() == null ? 10 : query.pageSize()
        );

        // 2. 构造查询条件
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

        // 【名称模糊搜索】
        wrapper.like(query.name() != null && !query.name().trim().isEmpty(), Product::getName, query.name());

        // 【状态多选】数据库是普通字符串，直接用 IN 即可
        wrapper.in(query.statuses() != null && !query.statuses().isEmpty(), Product::getStatus, query.statuses());

        // 【分类多选】处理 JSON 数组字段，使用 wrapper.and 构造括号内部的 OR 逻辑
        if (query.categories() != null && !query.categories().isEmpty()) {
            wrapper.and(w -> {
                for (int i = 0; i < query.categories().size(); i++) {
                    if (i > 0) {
                        w.or(); // 从第二个条件开始追加 OR
                    }
                    w.apply("JSON_CONTAINS(category, {0})", "\"" + query.categories().get(i) + "\"");
                }
            });
        }

        // 【IP多选】同样处理 JSON 数组字段
        if (query.ips() != null && !query.ips().isEmpty()) {
            wrapper.and(w -> {
                for (int i = 0; i < query.ips().size(); i++) {
                    if (i > 0) {
                        w.or();
                    }
                    w.apply("JSON_CONTAINS(ip, {0})", "\"" + query.ips().get(i) + "\"");
                }
            });
        }

        // 3. 排序：按创建时间倒序，最新上架的商品排在最前面
        wrapper.orderByDesc(Product::getCreatedAt);

        // 4. 执行查询并返回
        return R.ok(productService.page(page, wrapper));
    }


    // ================= 以下为后台管理使用的基础接口 =================

    // 获取所有商品（不分页，可作为后台简易列表备用）
    @GetMapping
    public R<List<Product>> list() {
        return R.ok(productService.list());
    }

    // 新增商品
    @PostMapping
    public R<Product> add(@RequestBody Product product) {
        boolean success = productService.save(product);
        return success ? R.ok(product) : R.error("添加失败");
    }

    // 修改商品
    @PutMapping("/{id}")
    public R<Product> update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        boolean success = productService.updateById(product);
        return success ? R.ok(product) : R.error("修改失败");
    }

    // 删除商品
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean success = productService.removeById(id);
        return success ? R.ok(null) : R.error("删除失败");
    }
}