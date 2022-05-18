package com.example.caigou.service;

import com.example.caigou.entity.Cart;
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
public interface ICartService extends IService<Cart> {
    public void addToCart(int comId,int userId);

    public List<Map<String,Object>> myCart(int userId);

    public void delete(int comId,int userId);
}
