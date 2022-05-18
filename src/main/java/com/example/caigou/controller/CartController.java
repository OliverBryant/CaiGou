package com.example.caigou.controller;


import com.example.caigou.entity.User;
import com.example.caigou.service.impl.CartServiceImpl;
import com.example.caigou.service.impl.CommodityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
@Controller
@RequestMapping("/caigou/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private CommodityServiceImpl commodityService;

    @RequestMapping(path = "/addToCart", method = RequestMethod.GET)
    public String addToCart(int comId, HttpSession session) {
        int userId = (int) session.getAttribute("loginUser");
        cartService.addToCart(comId, userId);
        return "redirect:/caigou/commodity/product?comId=" + comId;
    }

    @RequestMapping(path = "myCart", method = RequestMethod.GET)
    public String getCartPage(Model model,HttpSession session) {
        int userId = (int) session.getAttribute("loginUser");
        List<Map<String,Object>> list = cartService.myCart(userId);
//        System.out.println(list.toString());
        model.addAttribute("commodity",list);
        return "shoppingCart";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String delete(HttpSession session,int comId){
        int userId = (int) session.getAttribute("loginUser");
        cartService.delete(comId,userId);
        return "shoppingCart";
    }

    /*@RequestMapping(path = "/",method = RequestMethod.GET)
    public String p(){return "/shoppingCart";}*/
}