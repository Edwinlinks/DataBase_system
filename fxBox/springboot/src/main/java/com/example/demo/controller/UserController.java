package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Inbox;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.entity.vo.InBoxVO;
import com.example.demo.mapper.InboxMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserMapper userMapper;

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getName, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

    @PostMapping
    public Result<?> save(@RequestBody User user) {
        System.out.println(user);
        if (user.getUsername()==null||user.getUsername().equals("")){
            return Result.error("1","用户名不能为空");
        }
        if (user.getName()==null||user.getName().equals("")){
            return Result.error("1","姓名不能为空");
        }
        if (user.getPassword()==null||user.getPassword().equals("")){
            return Result.error("1","密码不能为空");
        }
        if (user.getEmail()==null||user.getEmail().equals("")){
            return Result.error("1","邮箱不能为空");
        }
        if (user.getPhone()==null||user.getPhone().equals("")){
            return Result.error("1","电话不能为空");
        }
        if (user.getStuId()==null){
            return Result.error("1","学号不能为空");
        }
        if (user.getStudioName()==null||user.getStudioName().equals("")){
            return Result.error("1","工作室不能为空");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        userMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(userMapper.selectById(id));
    }

}
