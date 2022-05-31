package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Box;
import com.example.demo.entity.Product;
import com.example.demo.entity.vo.InBoxVO;
import com.example.demo.entity.Inbox;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.BoxMapper;
import com.example.demo.mapper.InboxMapper;
import com.example.demo.mapper.ProductMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@RestController
@RequestMapping("/inbox")
public class InBoxController extends BaseController {
    @Resource
    private InboxMapper inboxMapper;
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
        // 复杂的模糊查询和连接查询需要自己写
        return Result.success(inboxMapper.findPage(search, new Page<>(pageNum, pageSize)));
    }

    @PostMapping
    // 新增
    public Result<?> save(@RequestBody InBoxVO inBoxVO) {
        System.out.println(inBoxVO);
        if (inBoxVO.getInQuantity()==null||inBoxVO.getInQuantity() < 1) {
            return Result.error("1", "添加数量非法");
        }
        String productName = inBoxVO.getInPname();
        if (productName==null||productName.equals("")){
            return Result.error("1", "产品名不能为空");
        }
        if (inBoxVO.getVueDate()==null){
            return Result.error("1", "入库日期不写?");
        }
        Inbox inbox = new Inbox();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(inBoxVO.getVueDate());
        try {
            Date date = simpleDateFormat.parse(format);
            inbox.setInDatetime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        QueryWrapper productQueryWrapper = new QueryWrapper();
        productQueryWrapper.eq("product_name", productName);
        Product product = productMapper.selectOne(productQueryWrapper);
        if (product == null) {
            //需要注册,系统开辟新柜子
            List<Box> list = boxMapper.emptyBoxList();
            System.out.println(list);
            if (list.size() == 0) {
                //没有空仓位
                return Result.error("1", "当前暂无空仓位");
            }
            Box box = list.get(0);
            productMapper.insert(new Product(productName));
            Product insertProduct = productMapper.selectOne(productQueryWrapper);//新注册的物品
            box.setProductId(insertProduct.getProductId());
            boxMapper.updateById(box);
            //插入入库记录
            inbox.setBoxId(box.getBoxId());
            inbox.setProductId(box.getProductId());
            QueryWrapper adminQuery=new QueryWrapper();
            adminQuery.eq("username",inBoxVO.getAdminUsername());
            Admin admin = adminMapper.selectOne(adminQuery);
            inbox.setAdminId(admin!=null?admin.getId():null);
            inbox.setInPname(inBoxVO.getInPname());
            inbox.setInQuantity(inBoxVO.getInQuantity());
            inboxMapper.insert(inbox);
             return Result.success("已为你找到存放新物品的仓位,仓位编号:" + box.getBoxId());
        } else {
            //还物品
            QueryWrapper boxQueryWrapper = new QueryWrapper();
            boxQueryWrapper.eq("product_id", product.getProductId());
            Box box = boxMapper.selectOne(boxQueryWrapper);
            //插入入库记录
            inbox.setBoxId(box.getBoxId());
            inbox.setProductId(box.getProductId());
            QueryWrapper adminQuery=new QueryWrapper();
            adminQuery.eq("username",inBoxVO.getAdminUsername());
            Admin admin = adminMapper.selectOne(adminQuery);
            inbox.setAdminId(admin!=null?admin.getId():null);
            inbox.setInPname(inBoxVO.getInPname());
            inbox.setInQuantity(inBoxVO.getInQuantity());

            inboxMapper.insert(inbox);

            return Result.success("归还仓位:"+box.getBoxId());
        }
    }

    @GetMapping("/{inId}")
    public Result<?> getById(@PathVariable Integer inId) {
        return Result.success(inboxMapper.selectById(inId));
    }

}
