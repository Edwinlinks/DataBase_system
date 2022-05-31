package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Product;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.utils.TokenUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RequestMapping("/product")
@RestController
public class ProductController {

    @Resource
    private ProductMapper productMapper;

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Product::getProductName, search); // mybatis plus 支持模糊查询
        }
        Page<Product> productPage = productMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(productPage);
    }

    @PostMapping
    public Result<?> save(
            @RequestBody Product product,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        System.out.println(product);
        if (product.getProductName()==null||product.getProductName().equals("")){
            return Result.error("1","请输入物品名");
        }
        QueryWrapper<Product> queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_name", product.getProductName());
        Product selectOne = productMapper.selectOne(queryWrapper);
        if (selectOne != null) {
            //该物品已存在
            return Result.error("1", "该物品名已注册");
        } else {
            //如果物品名没出现则插入
            productMapper.insert(product);
            Page<Product> productPage = productMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>());
            return Result.success(productPage);
        }
    }

    @PutMapping
    public Result<?> update(@RequestBody Product product) {
        if (product.getProductName()==null||product.getProductName().equals("")){
            return Result.error("1","请输入物品名");
        }
        productMapper.updateById(product);
        return Result.success();
    }

    @DeleteMapping("/{pid}")
    public Result<?> delete(@PathVariable Integer pid) {
        productMapper.deleteById(pid);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(productMapper.selectById(id));
    }

}
