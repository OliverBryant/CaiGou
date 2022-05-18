package com.example.caigou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.caigou.entity.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
public interface ICommodityService extends IService<Commodity> {

    public List<Map<String , Object>> myCommodity (int userId);

    public List<Map<String , Object>> guessYouLike (int userId);

    public List<Map<String, Object>> suggestByView ();

    public String releaseCommodity(String str,String title,String describe, Double price,String product_type,int userId);

    public List<Map<String,Object>> page(QueryWrapper<Commodity> queryWrapper,int current);

    public List<Map<String,Object>> research(String keyWord,int current);

    public Commodity product(int comId);

    public List<Map<String,Object>> myPublish(int userId);

    public int totalPage(QueryWrapper<Commodity> queryWrapper, int current);
}
