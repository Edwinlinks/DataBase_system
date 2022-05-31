package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Box;
import com.example.demo.entity.Product;
import com.example.demo.entity.vo.BoxVO;
import com.example.demo.entity.vo.InBoxVO;
import com.example.demo.mapper.BoxMapper;
import com.example.demo.mapper.ProductMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/box")
public class BoxController {
    @Resource
    private BoxMapper boxMapper;
    @Resource
    private ProductMapper productMapper;


    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        return Result.success(boxMapper.findPage(search, new Page<>(pageNum, pageSize)));
    }

    @PostMapping //更新或查询请求
    public Result<?> save(@RequestBody BoxVO boxVO) {
        // 开辟仓位
        System.out.println(boxVO);
        if (boxVO.getBoxAddr() == null||boxVO.getBoxAddr().equals("")) {
            return Result.error("1", "亲,请输出你要开辟的仓位");
        }
        QueryWrapper boxQueryWrapper = new QueryWrapper();
        boxQueryWrapper.eq("box_addr", boxVO.getBoxAddr());
        Box box = boxMapper.selectOne(boxQueryWrapper);
        if (box != null) {
            return Result.error("1", "该仓位已存在,开辟失败");
        }
        if (boxVO.getProductName() == null||boxVO.getProductName().equals("")) {
            boxMapper.insert(new Box(boxVO.getBoxAddr(), null));
            return Result.success("空仓位开辟成功,快快去装东西吧");
        }
        //如果有物品,判断是否注册
        QueryWrapper productQueryWrapper = new QueryWrapper();
        productQueryWrapper.eq("product_name", boxVO.getProductName());
        Product product = productMapper.selectOne(productQueryWrapper);
        if (product == null) {
            productMapper.insert(new Product(boxVO.getProductName()));
        }
        Product newProduct = productMapper.selectOne(productQueryWrapper);
        boxMapper.insert(new Box(boxVO.getBoxAddr(), newProduct.getProductId()));
        return Result.success("物品装载成功");
    }

    /**
     * 若新物品ID已存在,为非法操作  若新物品ID为新ID,注销旧物品,插入新物品
     *
     * @param boxVO
     * @return
     */
    @PutMapping  //更新请求
    public Result<?> update(@RequestBody BoxVO boxVO) {
        // 编辑仓位信息
        System.out.println(boxVO);
        if (boxVO.getProductName() == null||boxVO.getProductName().equals("")) {
            return Result.error("1", "请输入物品名");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_name", boxVO.getProductName());
        Product product = productMapper.selectOne(queryWrapper);
        StringBuilder data = new StringBuilder();
        if (product != null) {
            return Result.error("1", product.getProductId() == boxVO.getProductId() ? "说好的换物品呢" : "已存在装该物品的仓位");
        } else {
            QueryWrapper<Box> boxQuery = new QueryWrapper();
            boxQuery.eq("box_addr", boxVO.getBoxAddr());
            Box box = boxMapper.selectOne(boxQuery);
            if (box.getBoxId() != boxVO.getBoxId()) {
                //在别的仓位装新物品,
                return Result.error("1", "不能改仓位,要想开辟仓位请选择添加");
            } else {
                //地址没改,在原来的仓位上放新物品
                if (box.getProductId() != null) {
                    //原来装有物品,注销旧物品,插入新物品
                    productMapper.deleteById(box.getProductId());
                    productMapper.insert(new Product(boxVO.getProductName()));
                    QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
                    productQueryWrapper.eq("product_name",boxVO.getProductName());
                    Product newProduct = productMapper.selectOne(productQueryWrapper);
                    box.setProductId(newProduct.getProductId());
                    boxMapper.updateById(box);
                    data.append("旧物品已移出,");
                } else {
                    //原来没放物品
                    productMapper.insert(new Product(boxVO.getProductName()));
                    QueryWrapper productQuery = new QueryWrapper();
                    productQuery.eq("product_name", boxVO.getProductName());
                    Product newProduct = productMapper.selectOne(productQuery);
                    box.setProductId(newProduct.getProductId());
                    boxMapper.updateById(box);
                }
            }
        }
        return Result.success(data.toString() + "修改成功");
    }

    @DeleteMapping("/{boxid}")
    public Result<?> delete(@PathVariable Integer boxid) {
        Box box = boxMapper.selectById(boxid);
        StringBuilder data = new StringBuilder();
        if (box.getProductId() != null) {
            //  若有物品，同时删除其ID
            productMapper.deleteById(box.getProductId());
            data.append("该仓位的物品已删除,");
        }
        boxMapper.deleteById(boxid);
        data.append("该仓位已删除");
        return Result.success(data.toString());
    }

    @GetMapping("/{bid}")
    public Result<?> getById(@PathVariable Integer bid) {
        return Result.success(boxMapper.selectById(bid));
    }

}
