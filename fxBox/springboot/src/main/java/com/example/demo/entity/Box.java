package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//@Data
@TableName("box")
public class Box {
    @TableId(type = IdType.AUTO)
    @TableField(value = "box_id")
    private Integer boxId;
    @TableField(value = "box_addr")
    private String boxAddr;
    @TableField(value = "product_id")
    private Integer productId;

    public Box(String boxAddr, Integer productId) {
        this.boxAddr = boxAddr;
        this.productId = productId;
    }

    public Box(Integer productId) {
        this.productId = productId;
    }

    public Box() {
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
}
