package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.*;
import com.example.demo.entity.vo.BrokenVO;
import com.example.demo.entity.vo.PurchaseVO;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.PurchaseMapper;
import com.example.demo.mapper.SupplisersMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Resource
    private PurchaseMapper purchaseMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private SupplisersMapper supplisersMapper;
    @Resource
    private AdminMapper adminMapper;


    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        if (StringUtils.isNotBlank(search)) {
            Page<PurchaseVO> pageByProductName = purchaseMapper.findPageByProductName(search, new Page<PurchaseVO>(pageNum, pageSize));
            System.out.println(pageByProductName.getRecords());
            return Result.success(pageByProductName);
        } else {
            Page<PurchaseVO> page = purchaseMapper.findPage(search, new Page<PurchaseVO>(pageNum, pageSize));
            System.out.println(page.getRecords());
            return Result.success(page);
        }
    }

    @PostMapping
    public Result<?> save(@RequestBody PurchaseVO purchaseVO) throws ParseException {
        System.out.println(purchaseVO);
        if (purchaseVO.getProductName()==null){
            return Result.error("1","请输入产品名");
        }
        if (purchaseVO.getCompanyName()==null){
            return Result.error("1","请输入供应商");
        }
        if (purchaseVO.getVueDate()==null){
            return Result.error("1","请输入日期");
        }
        //若产品名或供应商不存在，注册
        QueryWrapper productQueryWrapper = new QueryWrapper();
        productQueryWrapper.eq("product_name", purchaseVO.getProductName());
        Product product = productMapper.selectOne(productQueryWrapper);
        if (product == null) {
            productMapper.insert(new Product(purchaseVO.getProductName()));
        }
        QueryWrapper supplyQueryWrapper = new QueryWrapper();
        supplyQueryWrapper.eq("company_name", purchaseVO.getCompanyName());
        Suppliers suppliers = supplisersMapper.selectOne(supplyQueryWrapper);
        if (suppliers == null) {
            supplisersMapper.insert(new Suppliers(purchaseVO.getCompanyName()));
        }

        //插入购买记录
        QueryWrapper adminQueryWrapper = new QueryWrapper();
        adminQueryWrapper.eq("username", purchaseVO.getAdminUsername());
        Admin admin = adminMapper.selectOne(adminQueryWrapper);
        Suppliers newSuppliers = supplisersMapper.selectOne(supplyQueryWrapper);
        Product newProduct = productMapper.selectOne(productQueryWrapper);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(purchaseVO.getVueDate());
        Date purchaseDate = simpleDateFormat.parse(format);
        purchaseMapper.insert(new Purchase(admin != null ? admin.getId() : null, newSuppliers.getSupplyId(), newProduct.getProductId(), purchaseDate));
        return Result.success("添加成功");
    }

    @PutMapping
    public Result<?> update(@RequestBody PurchaseVO purchaseVO) throws ParseException {
        System.out.println(purchaseVO);
        if (purchaseVO.getProductName()==null){
            return Result.error("1","请输入产品名");
        }
        if (purchaseVO.getCompanyName()==null){
            return Result.error("1","请输入供应商");
        }
        Purchase purchase = purchaseMapper.selectById(purchaseVO.getPurchaseId());
        //若产品名或供应商不存在，注册
        QueryWrapper productQueryWrapper = new QueryWrapper();
        productQueryWrapper.eq("product_name", purchaseVO.getProductName());
        Product product = productMapper.selectOne(productQueryWrapper);
        if (product == null) {
            productMapper.insert(new Product(purchaseVO.getProductName()));
        }
        QueryWrapper supplyQueryWrapper = new QueryWrapper();
        supplyQueryWrapper.eq("company_name", purchaseVO.getCompanyName());
        Suppliers suppliers = supplisersMapper.selectOne(supplyQueryWrapper);
        if (suppliers == null) {
            supplisersMapper.insert(new Suppliers(purchaseVO.getCompanyName()));
        }
        //修改购买记录
        QueryWrapper adminQueryWrapper = new QueryWrapper();
        adminQueryWrapper.eq("username", purchaseVO.getAdminUsername());
        Admin admin = adminMapper.selectOne(adminQueryWrapper);
        Suppliers newSuppliers = supplisersMapper.selectOne(supplyQueryWrapper);
        Product newProduct = productMapper.selectOne(productQueryWrapper);
        purchase.setAdminId(admin != null ? admin.getId() : null);
        purchase.setSupplyId(newSuppliers.getSupplyId());
        purchase.setProductId(newProduct.getProductId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(purchaseVO.getVueDate());
        Date purchaseDate = simpleDateFormat.parse(format);
        purchase.setPurchaseDate(purchaseDate);
        purchaseMapper.updateById(purchase);
        return Result.success("修改成功");
    }

//    @DeleteMapping("/{purchaseId}")
//    public Result<?> delete(@PathVariable Integer purchaseId) {
//        purchaseMapper.deleteById(purchaseId);
//        return Result.success();
//    }

    @GetMapping("/{purchaseId}")
    public Result<?> getById(@PathVariable Integer purchaseId) {
        return Result.success(purchaseMapper.selectById(purchaseId));
    }
}
