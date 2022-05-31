package com.example.demo.mapper.vo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Broken;
import com.example.demo.entity.vo.BrokenVO;
import com.example.demo.entity.vo.InBoxVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BrokenVOMapper extends BaseMapper<BrokenVO> {
    @Select("SELECT  b.*,p.`product_name`,a.`username` admin_username,box.box_addr\n" +
            "FROM brokens b , products p , admin a,box \n" +
            "WHERE b.product_id=p.product_id AND  box.`product_id`=p.`product_id` AND  b.`admin_id`=a.`id`\n")
    Page<BrokenVO> findPage(@Param("search") String search, @Param("page") Page<BrokenVO> page);
    @Select("SELECT  b.*,p.`product_name` ,a.`username` admin_username,box.box_addr\n" +
            "FROM brokens b , products p , admin a,box \n" +
            "WHERE b.product_id=p.product_id AND  box.`product_id`=p.`product_id` AND  b.`admin_id`=a.`id`" +
            "and  product_name LIKE CONCAT('%', #{search}, '%')")
    Page<BrokenVO> findPageByProductName(@Param("search") String search, @Param("page") Page<BrokenVO> page);
}
