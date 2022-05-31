package com.example.demo.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
public class TokenUtils {

    @Autowired
    private AdminMapper adminMapper;

    private static AdminMapper staticUserMapper;

    @PostConstruct
    public void init() {
        staticUserMapper = adminMapper;
    }

    /**
     * 生成token
     *
     * @param admin
     * @return
     */
    public static String genToken(Admin admin) {
        return JWT.create().withExpiresAt(DateUtil.offsetDay(new Date(), 1)).withAudience(admin.getId().toString())
                .sign(Algorithm.HMAC256(admin.getPassword()));
    }

    /**
     * 获取token中的用户信息
     *
     * @return
     */
    public static Admin getAdmin() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            String aud = JWT.decode(token).getAudience().get(0);
            Integer userId = Integer.valueOf(aud);
            return staticUserMapper.selectById(userId);
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }
}
