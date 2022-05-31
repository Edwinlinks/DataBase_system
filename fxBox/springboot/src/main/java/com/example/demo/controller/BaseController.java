package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 根据token获取用户信息
     * @return user
     */
    public Admin getUser() {
        String token = request.getHeader("token");
        String aud = JWT.decode(token).getAudience().get(0);
        Integer userId = Integer.valueOf(aud);
        return adminMapper.selectById(userId);
    }
}
