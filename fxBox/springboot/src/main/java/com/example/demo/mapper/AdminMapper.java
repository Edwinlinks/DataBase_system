package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Admin;
import com.example.demo.entity.vo.ProductCountVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    @Select("SELECT COUNT(product_name) COUNT, product_name FROM products GROUP BY product_name")
    List<ProductCountVO> countProducts();
}
