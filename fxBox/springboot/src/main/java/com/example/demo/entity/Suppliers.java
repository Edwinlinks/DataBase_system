package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//@Data
@TableName("suppliers")
public class Suppliers {
    @TableId(type = IdType.AUTO)
    private Integer supplyId;
    @TableField("company_name")
    private String companyName;
    @TableField("contact_name")
    private String contactName;
    @TableField("supply_phone")
    private String supplyPhone;
    @TableField("supply_addr")
    private String supplyAddr;

    public Suppliers(String companyName) {
        this.companyName = companyName;
    }

    public Suppliers(Integer supplyId, String companyName, String contactName, String supplyPhone, String supplyAddr) {
        this.supplyId = supplyId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.supplyPhone = supplyPhone;
        this.supplyAddr = supplyAddr;
    }

    public Suppliers() {
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getSupplyPhone() {
        return supplyPhone;
    }

    public void setSupplyPhone(String supplyPhone) {
        this.supplyPhone = supplyPhone;
    }

    public String getSupplyAddr() {
        return supplyAddr;
    }

    public void setSupplyAddr(String supplyAddr) {
        this.supplyAddr = supplyAddr;
    }
}
