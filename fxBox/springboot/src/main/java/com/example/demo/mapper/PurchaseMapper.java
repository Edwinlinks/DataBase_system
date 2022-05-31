package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.vo.BrokenVO;
import com.example.demo.entity.vo.PurchaseVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PurchaseMapper extends BaseMapper<Purchase> {
    @Select("SELECT purchases.*,products.*,suppliers.*,admin.`username` admin_username\n" +
            "FROM purchases ,suppliers,admin,products\n" +
            "WHERE purchases.supply_id=suppliers.supply_id \n" +
            "AND purchases.product_id = products.product_id")
    Page<PurchaseVO> findPage(@Param("search") String search, @Param("page") Page<PurchaseVO> page);

    @Select("SELECT purchases.*,products.*,suppliers.*,admin.`username` admin_username\n" +
            "FROM purchases ,suppliers,admin,products\n" +
            "WHERE purchases.supply_id=suppliers.supply_id \n" +
            "AND purchases.product_id = products.product_id\n" +
            "AND  product_name LIKE CONCAT('%', #{search}, '%')")
    Page<PurchaseVO> findPageByProductName(@Param("search") String search, @Param("page") Page<PurchaseVO> page);
}
