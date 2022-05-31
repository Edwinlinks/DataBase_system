package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("products")
public class Product {
    @TableId(type = IdType.AUTO)
    private Integer productId;
    @TableField("product_name")
    private String productName;
    private String intro;//简介

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Product(String productName) {
        this.productName = productName;
    }

    public Product(String productName, String intro) {
        this.productName = productName;
        this.intro = intro;
    }

    public Product(Integer productId, String productName, String intro) {
        this.productId = productId;
        this.productName = productName;
        this.intro = intro;
    }

    public Product(Integer productId) {
        this.productId = productId;
    }

    public Product() {
    }
}
