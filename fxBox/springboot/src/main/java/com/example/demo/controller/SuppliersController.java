package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Suppliers;
import com.example.demo.entity.User;
import com.example.demo.mapper.SupplisersMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/suppliers")
public class SuppliersController {

    @Resource
    private SupplisersMapper supplisersMapper;

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<Suppliers> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Suppliers::getCompanyName, search);
        }
        Page<Suppliers> suppliersPage = supplisersMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(suppliersPage);
    }

    @PostMapping
    public Result<?> save(@RequestBody Suppliers suppliers) {
        if (suppliers.getCompanyName()==null||suppliers.getCompanyName().equals("")){
            return Result.error("1","公司名不能为空");
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("company_name",suppliers.getCompanyName());
        Suppliers suppliers1 = supplisersMapper.selectOne(queryWrapper);
        if (suppliers1!=null){
            return Result.error("1","该供应商已存在");
        }
        supplisersMapper.insert(suppliers);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Suppliers suppliers) {
        if (suppliers.getCompanyName()==null||suppliers.getCompanyName().equals("")){
            return Result.error("1","公司名不能为空");
        }
        supplisersMapper.updateById(suppliers);
        return Result.success();
    }

    @DeleteMapping("/{supplyId}")
    public Result<?> delete(@PathVariable Integer supplyId) {
        supplisersMapper.deleteById(supplyId);
        return Result.success();
    }

    @GetMapping("/{supplyId}")
    public Result<?> getById(@PathVariable Integer supplyId) {
        return Result.success(supplisersMapper.selectById(supplyId));
    }
}
