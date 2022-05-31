package com.example.demo.entity.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
//@Data
public class InBoxVO {
    @TableField("in_id")
    @TableId(type = IdType.AUTO)
    private Integer inId;
    @TableField("admin_id")
    private Integer adminId;
    @TableField("admin_username")
    private  String adminUsername;
    @TableField("product_id")
    private Integer productId;
    @TableField("box_id")
    private  Integer boxId;
    @TableField(value = "in_pname")
    private String inPname;
    @TableField("in_quantity")
    private Integer inQuantity;
    @TableField(value = "in_datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inDatetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date vueDate;

    public Integer getInId() {
        return inId;
    }

    public void setInId(Integer inId) {
        this.inId = inId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
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

    public String getInPname() {
        return inPname;
    }

    public void setInPname(String inPname) {
        this.inPname = inPname;
    }

    public Integer getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Integer inQuantity) {
        this.inQuantity = inQuantity;
    }

    public Date getInDatetime() {
        return inDatetime;
    }

    public void setInDatetime(Date inDatetime) {
        this.inDatetime = inDatetime;
    }

    public Date getVueDate() {
        return vueDate;
    }

    public void setVueDate(Date vueDate) {
        this.vueDate = vueDate;
    }
}
