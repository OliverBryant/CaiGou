package com.example.caigou.service;

import com.example.caigou.entity.Orders;
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
public interface IOrderService extends IService<Orders> {

    public int createOrder(String address,int userId,String id,int total,int status);

    public List<Map<String,Object>> myOrders(int userId);
}
