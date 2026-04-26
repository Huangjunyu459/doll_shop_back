package com.doll_shop.controller;

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

    // GET /api/products → 前端展示页 + 后台管理页共用（前端自行筛选）
    @GetMapping
    public R<List<Product>> list() {
        List<Product> list = productService.list();
        return R.ok(list);
    }

    // 新增商品（后台管理使用）
    @PostMapping
    public R<Product> add(@RequestBody Product product) {
        boolean success = productService.save(product);
        if (success) {
            return R.ok(product);
        }
        return R.error("添加失败");
    }

    // 修改商品
    @PutMapping("/{id}")
    public R<Product> update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        boolean success = productService.updateById(product);
        if (success) {
            return R.ok(product);
        }
        return R.error("修改失败");
    }

    // 删除商品
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean success = productService.removeById(id);
        if (success) {
            return R.ok(null);
        }
        return R.error("删除失败");
    }
}