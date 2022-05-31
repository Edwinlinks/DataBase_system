package com.example.demo.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

//@Data
public class BrokenVO {
    @TableId(type = IdType.AUTO)
    private Integer brokenId;
    @TableField("admin_username")
    private String adminUsername;
    @TableField("product_name")
    private String productName;
    @TableField("box_id")
    private Integer boxId;
    @TableField("broken_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date brokenDate;
    @TableField("box_addr")
    private String boxAddr;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date vueDate;

    public Integer getBrokenId() {
        return brokenId;
    }

    public void setBrokenId(Integer brokenId) {
        this.brokenId = brokenId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getBoxAddr() {
        return boxAddr;
    }

    public void setBoxAddr(String boxAddr) {
        this.boxAddr = boxAddr;
    }

    public Date getVueDate() {
        return vueDate;
    }

    public void setVueDate(Date vueDate) {
        this.vueDate = vueDate;
    }
}
