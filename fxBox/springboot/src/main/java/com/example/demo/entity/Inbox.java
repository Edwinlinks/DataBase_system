package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.checkerframework.checker.formatter.qual.Format;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

//@Data
@TableName("inbox")
public class Inbox {
    @TableField("in_id")
    @TableId(type = IdType.AUTO) //声明自增字段
    private Integer inId;
    private  Integer adminId;
    private Integer productId;
    private  Integer boxId;

    @TableField(value = "in_pname")
    private String inPname;

    @TableField("in_quantity")
    private Integer inQuantity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("in_datetime")
    private Date inDatetime;

    public Inbox(Integer adminId, Integer productId, Integer boxId, String inPname, Integer inQuantity, Date inDatetime) {
        this.adminId = adminId;
        this.productId = productId;
        this.boxId = boxId;
        this.inPname = inPname;
        this.inQuantity = inQuantity;
        this.inDatetime = inDatetime;
    }

    public Inbox() {
    }


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
}
