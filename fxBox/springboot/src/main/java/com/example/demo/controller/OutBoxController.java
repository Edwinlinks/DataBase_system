package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Box;
import com.example.demo.entity.Outbox;
import com.example.demo.entity.Product;
import com.example.demo.entity.vo.InBoxVO;
import com.example.demo.entity.vo.OutBoxVO;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.BoxMapper;
import com.example.demo.mapper.OutboxMapper;
import com.example.demo.mapper.ProductMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/outbox")
public class OutBoxController {
    @Resource
    private OutboxMapper outboxMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private BoxMapper boxMapper;
    @Resource
    private AdminMapper adminMapper;

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        return Result.success(outboxMapper.findPage(search,new Page<>(pageNum,pageSize)));
    }

    @PostMapping
    public Result<?> save(@RequestBody OutBoxVO outBoxVO) {
        System.out.println(outBoxVO);
        String productName;
        productName = outBoxVO.getProductName();
        if (productName==null||productName.equals("")){
            return Result.error("1","产品名要写");
        }
        QueryWrapper productQueryWrapper = new QueryWrapper();
        productQueryWrapper.eq("product_name", productName);
        Product product = productMapper.selectOne(productQueryWrapper);
        if (product == null) {
            //输入产品名有错
            return Result.error("1","输入的产品名有错");
        } else {
            //借物品
            QueryWrapper boxQueryWrapper = new QueryWrapper();
            boxQueryWrapper.eq("product_id", product.getProductId());
            Box box = boxMapper.selectOne(boxQueryWrapper);
            if(box==null){
                return Result.error("1","该物品还没有入库");
            }
            Outbox outbox = new Outbox();
            outbox.setBoxId(box.getBoxId());
            QueryWrapper adminQuery=new QueryWrapper();
            adminQuery.eq("username",outBoxVO.getName());
            Admin admin = adminMapper.selectOne(adminQuery);
            outbox.setUserId(admin!=null?admin.getId():null);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(outBoxVO.getVueDate());
            try {
                Date date = simpleDateFormat.parse(format);
                outbox.setOutDatatime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            outboxMapper.insert(outbox);
            //弹出相对应仓位
            return Result.success("物品存放仓位:"+box.getBoxId());
        }
    }
//
//    @DeleteMapping("/{outId}")
//    public Result<?> delete(@PathVariable Integer outId) {
//        outboxMapper.deleteById(outId);
//        return Result.success();
//    }

    @GetMapping("/{outId}")
    public Result<?> getById(@PathVariable Integer outId) {
        return Result.success(outboxMapper.selectById(outId));
    }
}
