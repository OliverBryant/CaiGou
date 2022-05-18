package com.example.caigou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.caigou.entity.Commodity;
import com.example.caigou.entity.User;
import com.example.caigou.mapper.CommodityMapper;
import com.example.caigou.mapper.UserMapper;
import com.example.caigou.service.ICommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Map<String, Object>> myCommodity(int userId) {

        User user = userMapper.selectById(userId);

        List<Map<String, Object>> list = commodityMapper.departmentSuggesstion(user.getUserDepartment());

        List<Map<String, Object>> map = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            map.add(list.get(i));
        }

        return map;
    }

    @Override
    public List<Map<String, Object>> guessYouLike(int userId) {
        int count = 0, tag = 0;
        List<Map<String, Object>> list = new ArrayList<>();
        while (count < 8) {
            if (tag > 7) {
                break;
            }
            List<Map<String, Object>> temp = commodityMapper.guessYouLike(userId, tag);
            for (Map<String, Object> map : temp) {
                list.add(map);
                count++;
            }
            tag++;
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> suggestByView() {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("com_id", "com_name", "com_image", "com_price")
                .orderByAsc("com_view");

        Page page = new Page<>(0, 8);

        IPage<Map<String, Object>> mapIPage = commodityMapper.selectMapsPage(page, queryWrapper);

//        List<Map<String, Object>> list = commodityMapper.selectMaps(queryWrapper);

        return mapIPage.getRecords();
    }

    @Override
    public String releaseCommodity(String str, String title, String describe, Double price, String product_type, int userId) {

        String[] s = str.split(",");

        Commodity commodity = new Commodity();
        commodity.setComImage(s[0]);
        if (s.length > 1) {
            commodity.setComPic2(s[1]);
            if (s.length > 2) {
                commodity.setComPic3(s[2]);
                if (s.length > 3) {
                    commodity.setComPic4(s[3]);
                }
            }
        }
        commodity.setComInfo(describe);
        commodity.setComPrice(price);
        commodity.setComName(title);
        commodity.setComTag(product_type);
        commodity.setUserId(userId);
        System.out.println(commodity);
        commodityMapper.insert(commodity);

        return "success";
    }

    @Override
    public List<Map<String, Object>> page(QueryWrapper<Commodity> queryWrapper, int current) {
        Page page = new Page<>(current, 9);

        IPage<Map<String, Object>> mapIPage = commodityMapper.selectMapsPage(page, queryWrapper);

        System.out.println("总页数" + mapIPage.getPages());
        System.out.println("总记录数" + mapIPage.getTotal());
        List<Map<String, Object>> records = mapIPage.getRecords();
        records.forEach(System.out::println);

        return records;
    }

    @Override
    public int totalPage(QueryWrapper<Commodity> queryWrapper, int current) {
        Page page = new Page<>(current, 9);

        IPage<Map<String, Object>> mapIPage = commodityMapper.selectMapsPage(page, queryWrapper);

        System.out.println("总页数" + mapIPage.getPages());
        System.out.println("总记录数" + mapIPage.getTotal());
        List<Map<String, Object>> records = mapIPage.getRecords();
        records.forEach(System.out::println);

        return (int) mapIPage.getPages();
    }

    @Override
    public List<Map<String, Object>> research(String keyword, int current) {
        Page page = new Page<>(current, 9);

        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("com_status", 2)
                .like("com_name", keyword);

        IPage<Map<String, Object>> mapIPage = commodityMapper.selectMapsPage(page, queryWrapper);
        return mapIPage.getRecords();
    }

    @Override
    public Commodity product(int comId) {
        Commodity commodity = commodityMapper.selectById(comId);
        return commodity;
    }

    @Override
    public List<Map<String, Object>> myPublish(int userId) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .select("com_id", "com_name", "com_price", "com_image", "com_status");
        return commodityMapper.selectMaps(queryWrapper);
    }

}
