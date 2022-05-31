package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Box;
import com.example.demo.entity.vo.BoxVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoxMapper extends BaseMapper<Box> {
    @Select("SELECT box.*,products.`product_name` FROM  box  LEFT JOIN products\n" +
            "ON box.`product_id`=products.`product_id` \n" +
            "WHERE box_addr LIKE  CONCAT('%', #{search}, '%')")
    Page<BoxVO> findPage(String search, Page<BoxVO> page);

    @Select("SELECT * FROM box WHERE ISNULL(product_id)")
    List<Box> emptyBoxList();
}
