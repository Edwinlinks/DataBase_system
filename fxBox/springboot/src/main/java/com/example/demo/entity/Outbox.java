package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

//@Data
@TableName("outbox")
public class Outbox {
    @TableField("out_id")
    @TableId(type = IdType.AUTO)
    private Integer outId;
    private  Integer userId;
    private  Integer boxId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("out_datetime")
    private Date outDatatime;


    public Outbox() {
    }

    public Integer getOutId() {
        return outId;
    }

    public void setOutId(Integer outId) {
        this.outId = outId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public Date getOutDatatime() {
        return outDatatime;
    }

    public void setOutDatatime(Date outDatatime) {
        this.outDatatime = outDatatime;
    }
}
