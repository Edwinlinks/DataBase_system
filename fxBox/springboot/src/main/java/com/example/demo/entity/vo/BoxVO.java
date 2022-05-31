package com.example.demo.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

//@Data
public class BoxVO {
    @TableField(value = "box_id")
    private Integer boxId;
    @TableField("box_addr")
    private String boxAddr;
    @TableField(value = "productId")
    private Integer productId;
    @TableField(value = "product_name")
    private String productName;


    public BoxVO() {
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public String getBoxAddr() {
        return boxAddr;
    }

    public void setBoxAddr(String boxAddr) {
        this.boxAddr = boxAddr;
    }

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
}
