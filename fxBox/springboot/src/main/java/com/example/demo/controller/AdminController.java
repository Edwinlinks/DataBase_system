package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.*;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Resource
    AdminMapper adminMapper;
    @Resource
    PermissionMapper permissionMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin adminParam) {
        // 得到前端的数据
        // System.out.println(adminParam);
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("username", adminParam.getUsername());
        adminQueryWrapper.eq("password", adminParam.getPassword());
        // 执行SQL
        Admin res = adminMapper.selectOne(adminQueryWrapper);
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        // 1. 从user_role表通过用户id查询所有的角色信息
        // 对资源根据id进行排序
        //设置当前用户的资源信息
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        List<Permission> permissions = permissionMapper.selectList(permissionQueryWrapper);
        res.setPermissions(permissions);

        // 生成token
        String token = TokenUtils.genToken(res);
        res.setToken(token);

        return Result.success(res);
    }


    @PostMapping
    public Result<?> save(@RequestBody Admin admin) {
        if (admin.getPassword() == null) {
            admin.setPassword("123456");
        }
        adminMapper.insert(admin);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Admin admin) {
        adminMapper.updateById(admin);
        return Result.success();
    }


    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        adminMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(adminMapper.selectById(id));
    }

    @GetMapping("/all")
    public Result<?> findAll() {
        return Result.success(adminMapper.selectList(null));
    }

    /**
     * 统计数据
     *
     * @return
     */
    @GetMapping("/count")
    public Result<?> count() {
        return Result.success(adminMapper.countProducts()); // 当前物资数量信息
    }

}
