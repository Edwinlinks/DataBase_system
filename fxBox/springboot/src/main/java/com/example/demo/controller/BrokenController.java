package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.*;
import com.example.demo.entity.vo.BrokenVO;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.BoxMapper;
import com.example.demo.mapper.BrokenMapper;
import com.example.demo.mapper.vo.BrokenVOMapper;
import com.example.demo.mapper.ProductMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/broken")
public class BrokenController {
    @Resource
    private BrokenVOMapper brokenVOMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private BoxMapper boxMapper;
    @Resource
    private BrokenMapper brokenMapper;
    @Resource
    private AdminMapper adminMapper;

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        if (StringUtils.isNotBlank(search)) {
            Page<BrokenVO> pageByProductName = brokenVOMapper.findPageByProductName(search, new Page<>(pageNum, pageSize));
            System.out.println(pageByProductName.getRecords());
            return Result.success(pageByProductName);
        } else {
            Page<BrokenVO> page = brokenVOMapper.findPage(search, new Page<>(pageNum, pageSize));
            System.out.println(page.getRecords());
            return Result.success(page);
        }
    }

    @PostMapping
    public Result<?> save(@RequestBody BrokenVO brokenVO) throws ParseException {
        System.out.println(brokenVO);
        //可以通过物品名或者仓位地址报损,对应product保留
        QueryWrapper boxQueryWrappe = new QueryWrapper();
        if (brokenVO.getBoxAddr() != null&&!brokenVO.getBoxAddr().equals("")) {
            boxQueryWrappe.eq("box_addr", brokenVO.getBoxAddr());
            Box box = boxMapper.selectOne(boxQueryWrappe);
            if (box == null) {
                return Result.error("1", "请输入正确的仓位地址");
            }
            QueryWrapper amdinQueryWrapper = new QueryWrapper();
            amdinQueryWrapper.eq("useranme", brokenVO.getAdminUsername());
            Admin admin = adminMapper.selectOne(amdinQueryWrapper);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(brokenVO.getVueDate());
            Date brokendate = simpleDateFormat.parse(format);
            brokenMapper.insert(new Broken(admin != null ? admin.getId() : null, box.getProductId(), brokendate));
            box.setProductId(null);
            boxMapper.updateById(box);
            return Result.success("报损成功,仓位" + box.getBoxId() + "已清空");
        } else if (brokenVO.getProductName() != null&&!brokenVO.getProductName().equals("")) {
            QueryWrapper productQueryWrapper = new QueryWrapper();
            productQueryWrapper.eq("product_name", brokenVO.getProductName());
            Product product = productMapper.selectOne(productQueryWrapper);
            if (product==null){
                return Result.error("1","请输入正确的报损物品");
            }
            boxQueryWrappe.eq("product_id", product.getProductId());
            Box box = boxMapper.selectOne(boxQueryWrappe);
            if (box == null) {
                return Result.error("1", "请输入正确的物品名");
            }
            QueryWrapper<Admin> amdinQueryWrapper = new QueryWrapper();
            amdinQueryWrapper.eq("username", brokenVO.getAdminUsername());
            Admin admin = adminMapper.selectOne(amdinQueryWrapper);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(brokenVO.getVueDate());
            Date brokendate = simpleDateFormat.parse(format);
            brokenMapper.insert(new Broken(admin != null ? admin.getId() : null, box.getProductId(), brokendate));
            box.setProductId(null);
            boxMapper.updateById(box);
            return Result.success("报损成功,仓位" + box.getBoxId() + "已清空");
        } else {
            return Result.error("1", "请填写物品名或者仓位地址");
        }
    }

//    @PutMapping
//    public Result<?> update(@RequestBody BrokenVO brokenVO) {
//        System.out.println(brokenVO);
//        return Result.success();
//    }

//    @DeleteMapping("/{brokenId}")
//    public Result<?> delete(@PathVariable Integer brokenId) {
//        brokenVOMapper.deleteById(brokenId);
//        return Result.success();
//    }

    @GetMapping("/{brokenId}")
    public Result<?> getById(@PathVariable Integer brokenId) {
        return Result.success(brokenVOMapper.selectById(brokenId));
    }
}
