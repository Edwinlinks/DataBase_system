package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

//@Data
@TableName("brokens")
public class Broken {
    @TableId(type = IdType.AUTO)
    private Integer BrokenId;
    @TableField("admin_id")
    private Integer adminId;
    @TableField("product_id")
    private Integer productId;
    @TableField(exist = false)
    private Integer boxId;
    @TableField("broken_date")
    private Date brokenDate;


    public Broken(Integer adminId, Integer productId, Date brokenDate) {
        this.adminId = adminId;
        this.productId = productId;
        this.brokenDate = brokenDate;
    }

    public Broken() {
    }

    public Integer getBrokenId() {
        return BrokenId;
    }

    public void setBrokenId(Integer brokenId) {
        BrokenId = brokenId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public Date getBrokenDate() {
        return brokenDate;
    }

    public void setBrokenDate(Date brokenDate) {
        this.brokenDate = brokenDate;
    }
}
