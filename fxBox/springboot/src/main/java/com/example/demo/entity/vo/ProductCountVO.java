package com.example.demo.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

//@Data
public class ProductCountVO {
    private Integer count;
    @TableField("product_name")
    private String productName;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
