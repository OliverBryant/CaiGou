package com.example.caigou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.caigou.entity.Commodity;
import com.example.caigou.entity.Orders;
import com.example.caigou.mapper.OrderMapper;
import com.example.caigou.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public int createOrder(String address,int userId,String id,int total,int status) {
        Orders order = new Orders();
        order.setBuyerId(userId);
        order.setOrderAddress(address);
        order.setComId(id);
        order.setOrderTotal(total);
        order.setOrderStatus(status);
        int result = orderMapper.insert(order);
        int orderId = order.getOrderId();
        System.out.println("orderId="+orderId);
        return orderId;
    }

    @Override
    public List<Map<String, Object>> myOrders(int userId) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_id", userId)
                .select("order_id","order_address","buyer_id","com_id","order_time","order_status","order_total");
        return orderMapper.selectMaps(queryWrapper);
    }
}
