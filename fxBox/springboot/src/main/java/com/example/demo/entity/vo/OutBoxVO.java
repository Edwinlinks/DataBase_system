package com.example.demo.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//@Data
public class OutBoxVO {

    @TableField("product_name")
    private  String productName;
    @TableField("out_id")
    private Integer outId;
    @TableField("box_id")
    private Integer boxId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "out_datetime")
    private Date outDatetime;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date vueDate;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOutId() {
        return outId;
    }

    public void setOutId(Integer outId) {
        this.outId = outId;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public Date getOutDatetime() {
        return outDatetime;
    }

    public void setOutDatetime(Date outDatetime) {
        this.outDatetime = outDatetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getVueDate() {
        return vueDate;
    }

    public void setVueDate(Date vueDate) {
        this.vueDate = vueDate;
    }
}
