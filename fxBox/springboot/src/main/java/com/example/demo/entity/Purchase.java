package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

//@Data
@TableName("purchases")
public class Purchase {
    @TableId(type = IdType.AUTO)
    private Integer purchase_id;
    @TableField("admin_id")
    private Integer adminId;
    @TableField("supply_id")
    private Integer supplyId;
    @TableField("product_id")
    private Integer productId;
    @TableField("purchase_date")
    private Date purchaseDate;

    public Purchase(Integer adminId, Integer supplyId, Integer productId, Date purchaseDate) {
        this.adminId = adminId;
        this.supplyId = supplyId;
        this.productId = productId;
        this.purchaseDate = purchaseDate;
    }

    public Purchase() {
    }

    public Integer getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(Integer purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
