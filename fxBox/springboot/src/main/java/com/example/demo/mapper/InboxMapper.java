package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Inbox;
import com.example.demo.entity.vo.InBoxVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface InboxMapper extends BaseMapper<Inbox> {
    @Select("\n" +
            "SELECT `inbox`.*, `admin`.`username` admin_username\n" +
            "FROM `inbox`  LEFT JOIN `admin` \n" +
            "ON `inbox`.`admin_id` = `admin`.`id` \n" +
            "WHERE in_pname LIKE  CONCAT('%', #{search}, '%')")
    Page<InBoxVO> findPage(@Param("search") String search, @Param("page") Page<InBoxVO> page);
}
