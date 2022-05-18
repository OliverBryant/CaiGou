package com.example.caigou.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.caigou.entity.Commodity;
import com.example.caigou.entity.Orders;
import com.example.caigou.service.impl.CartServiceImpl;
import com.example.caigou.service.impl.CommodityServiceImpl;
import com.example.caigou.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
@Controller
@RequestMapping("/caigou/order")
public class OrderController {

    @Autowired
    private CommodityServiceImpl commodityService;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private OrderServiceImpl orderService;

    @RequestMapping(path = "/confirm",method = RequestMethod.GET)
    public String confirmOrder(int total, String[] comId, String address,Model model, HttpSession session){
        String[] comm = null;
        String id = null;
        for (int i = 0; i < comId.length; i++) {
            comm=comId[i].split("_");
            comId[i] = comm[1];
            if(i==0){
                id=comm[1];
            } else {
                id=id+"_"+comm[1];
            }
        }

//        现在comId[]中存的传过来的id
        List<Map<String,Object>> list = new ArrayList<>();
        Commodity commodity = new Commodity();
        int userId = (int) session.getAttribute("loginUser");
        for (int i = 0; i < comId.length; i++) {
            commodity=commodityService.getById(comId[i]);
            Map<String,Object> map = new HashMap<>();
            map.put("com_id",comId[i]);
            map.put("com_image",commodity.getComImage());
            map.put("com_price",commodity.getComPrice());
            map.put("com_name",commodity.getComName());
            list.add(map);
            cartService.delete(Integer.parseInt(comId[i]),userId);
        }

        Orders order = new Orders();
        order.setOrderAddress(address);
        order.setBuyerId(userId);
        order.setComId(id);
        order.setOrderTotal(total);
        order.setOrderStatus(1);
        System.out.println(order.toString());
        int orderId = orderService.createOrder(address,userId,id,total,1);

        System.out.println(list.toString());

        model.addAttribute("commodity",list);
        model.addAttribute("total",total);
        model.addAttribute("orderId",orderId);
//        session.setAttribute("order",);

        return "order";
    }

    @RequestMapping(path = "/finish",method = RequestMethod.GET)
    public String finish(int orderId,String address){
        System.out.println(orderId);
        System.out.println(address);
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id",orderId)
                .set("order_address",address)
                .set("order_status",2);
        orderService.update(updateWrapper);

        Orders order ;
        order = orderService.getById(orderId);

        String[] str = order.getComId().split("_");
        for (int i = 0; i < str.length; i++) {
            UpdateWrapper<Commodity> queryWrapper = new UpdateWrapper<>();
            queryWrapper.eq("com_id",str[i])
                    .set("com_status",3);
            commodityService.update(queryWrapper);
        }

        return "redirect:/caigou/index";
    }

}
