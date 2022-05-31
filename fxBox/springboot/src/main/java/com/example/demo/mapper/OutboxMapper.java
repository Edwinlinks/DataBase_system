package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Outbox;
import com.example.demo.entity.vo.InBoxVO;
import com.example.demo.entity.vo.OutBoxVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface  OutboxMapper extends BaseMapper<Outbox> {
    @Select("SELECT products.`product_name`,t.* FROM products,(\n" +
            "            SELECT ou.*,box.product_id  FROM outbox ou,box\n" +
            "            WHERE   ou.box_id=box.`box_id`\n" +
            "            )AS t\n" +
            "            WHERE products.`product_id`=t.product_id AND product_name LIKE  CONCAT('%', #{search}, '%') ")
    Page<OutBoxVO> findPage(@Param("search") String search, @Param("page") Page<OutBoxVO> page);
}
