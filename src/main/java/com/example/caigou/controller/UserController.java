package com.example.caigou.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.caigou.entity.Commodity;
import com.example.caigou.entity.MailClient;
import com.example.caigou.entity.Orders;
import com.example.caigou.entity.User;
import com.example.caigou.service.impl.CommodityServiceImpl;
import com.example.caigou.service.impl.OrderServiceImpl;
import com.example.caigou.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.lang.String;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
@Controller
@RequestMapping("/caigou/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommodityServiceImpl commodityService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getUserLogin() {
        return "login";
    }

    @RequestMapping(path = "/pCommodity", method = RequestMethod.GET)
    public String getPCommodity(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("loginUser");
        Map<String, Object> map = userService.userInfo(userId);
        if (map.containsKey("success")) {
            User user = (User) map.get("user");
            model.addAttribute("user", user);
        }

        model.addAttribute("commodity", commodityService.myPublish(userId));

        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_id", userId);
        List<Orders> list = orderService.list(queryWrapper);
        System.out.println(list.toString());
        List<Map<String, Object>> maps = new ArrayList<>();

        Commodity commodity = new Commodity();
        String[] str;
        for (int i = 0; i < list.size(); i++) {
            str = list.get(i).getComId().split("_");
            System.out.println(str[0]);
            for (int j = 0; j < str.length; j++) {
                commodity = commodityService.getById(Integer.parseInt(str[j]));
//                System.out.println("!!!!!!!!!!"+commodity);
                Map<String, Object> temp = new HashMap<>();
                temp.put("com_image", commodity.getComImage());
                temp.put("com_name", commodity.getComName());
                temp.put("com_price", commodity.getComPrice());
                System.out.println(temp.toString());
                maps.add(temp);
                System.out.println(maps);
            }
        }
        System.out.println(maps);
        model.addAttribute("purchase", maps);

        List<Map<String,Object>> list1 = orderService.myOrders(userId);
        List<Map<String,Object>> list2 = new ArrayList<>();
        Map<String,Object> map1;
        for (int i = 0; i < list1.size(); i++) {
            map1 = list1.get(i);
            if (!map1.containsKey("order_address")){
                map1.put("order_address",null);
            }
            String[] str1 = map1.get("com_id").toString().split("_");
            map1.put("num",str1.length);
            /*QueryWrapper<Commodity> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("com_id",str1[0]);*/
            Commodity commodity1 = commodityService.getById(str1[0]);
            map1.put("com_image",commodity1.getComImage());
            list2.add(map1);
        }
        System.out.println("list2"+list2.toString());
        model.addAttribute("order",list2);


        return "/pCommodity";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String userLoginById(int userId, String userPassword, Model model, HttpSession session) {
        User user1 = userService.getById(userId);
        if (userId == 2018213000 && userPassword.equals("123456")&&user1.getUserStatus()==1) {
            System.out.println("1111111111111111111111");
            session.setAttribute("admin", userId);
            return "redirect:/caigou/commodity/admin";
//            System.out.println("2222222222222222222");
        }
        Map<String, Object> map = userService.userLogin(userId, userPassword);
        if (map.containsKey("success")) {
            User user = (User) map.get("user");
            model.addAttribute("user", user);
            session.setAttribute("tag", "all");
            session.setAttribute("loginUser", userId);
            session.setAttribute("userName", user.getUserName());
            session.setAttribute("userImage", user.getUserImage());
            return "redirect:/caigou/index";
        } else {
            System.out.println(map.get("Msg"));
            model.addAttribute("Msg", map.get("Msg"));
            return "login";
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/caigou/index";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegister(HttpSession session, Model model) {
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String userRegister(int userId, String userName, String userNick, String userPassword, String userDepartment, String userClass, String userEmail, Model model) {

        Map<String, Object> map = userService.userRegister(userId, userName, userNick, userPassword, userDepartment, userClass, userEmail);

        System.out.println(map.get("MSG"));

        if (map.containsKey("success")) {
            model.addAttribute("msg", "注册成功，请前往邮箱激活");
//            model.addAttribute("target","/login");
            return "/operate-result";
        }

        return "register";

    }

    @RequestMapping(path = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String userActivation(@PathVariable("userId") int userId ,@PathVariable("code") int code,Model model){
        User user = userService.getById(userId);
        if((int)userId/3%99999==code&&(user.getUserStatus()==3||user.getUserStatus()==0)){
            model.addAttribute("msg","激活成功，请登录使用");
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("user_status",1);
            userService.update(updateWrapper);

        } else if((int)userId/3%99999==code&&user.getUserStatus()==1){
            model.addAttribute("msg","账号已经激活，请直接登录");
        } else {
            model.addAttribute("msg","激活链接有误，请重新注册");
        }
        return "/operate-result";
    }

    @RequestMapping(path = "/image", method = RequestMethod.POST)
    public String userImage(MultipartFile uploadImage, Model model, HttpSession session) {

        JSONObject json = new JSONObject();
        try {
            if (uploadImage == null) {
                model.addAttribute("MSG", "上传失败，上传图片数据为空");
                return "redirect:/caigou/user/pCommodity";
            }
            String suffix = uploadImage.getContentType().toLowerCase();//图片后缀，用以识别哪种格式数据
            suffix = suffix.substring(suffix.lastIndexOf("/") + 1);

            if (suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("png") || suffix.equals("gif")) {
                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
                System.out.println(fileName);
                String imgFilePath = "E:/image/";//新生成的图片

                File targetFile = new File(imgFilePath, fileName);
                if (!targetFile.getParentFile().exists()) { //注意，判断父级路径是否存在
                    targetFile.getParentFile().mkdirs();
                }
                //保存
                uploadImage.transferTo(targetFile);

                model.addAttribute("success", "上传图片成功");

                Map<String, Object> map = userService.uploadToOSS(fileName, imgFilePath, "userPic", (int) session.getAttribute("loginUser"));
                if (map.containsKey("success")) {
                    UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                    updateWrapper
                            .eq("user_id", (int) session.getAttribute("loginUser"))
                            .set("user_image", map.get("url"));

                    userService.update(updateWrapper);
                    model.addAttribute("userPic", map.get("url"));
                } else {
                    String Msg = (String) map.get("MSG");
                    System.out.println(Msg);
                }
                System.out.println(map.get("url"));
                session.removeAttribute("userImage");
                session.setAttribute("userImage", map.get("url"));
                return "redirect:/caigou/user/pCommodity";
            }
            model.addAttribute("MSG", "系统异常，上传图片格式非法");
            return "redirect:/caigou/user/pCommodity";
        } catch (Exception e) {
            model.addAttribute("系统异常，上传图片失败");
            return "redirect:/caigou/user/pCommodity";
        }
    }

    @RequestMapping(path = "/reversePassword", method = RequestMethod.POST)
    public String reversePassword(String oldPassword, String newPassword, String reNewPassword, HttpSession session) {
        int userId = (int) session.getAttribute("loginUser");
        System.out.println(oldPassword + " " + newPassword + " " + reNewPassword);
        User user = userService.getById(userId);
        if (user.getUserPassword().equals(oldPassword) && newPassword.equals(reNewPassword)) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .eq("user_id", userId)
                    .set("user_password", newPassword);
            userService.update(updateWrapper);
            session.removeAttribute("loginUser");
            return "redirect:/caigou/index";
        } else {
            return "pCommodity";
        }
    }

    @RequestMapping(path = "/star", method = RequestMethod.POST)
    public void star(String rate, HttpSession session) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        User user = userService.getById((int) session.getAttribute("loginUser"));
        int rank = Integer.parseInt(rate);
        int temp = (user.getUserStar() + rank) / 2;
        System.out.println(temp);
        updateWrapper
                .eq("user_id", user.getUserId())
                .set("user_star", temp);
        userService.update(updateWrapper);
        System.out.println(rate);
        return;
    }

    @RequestMapping(path = "/code",method = RequestMethod.GET)
    @ResponseBody
    public int codeTest(Model model,HttpSession session){
        int userId = (int)session.getAttribute("loginUser");
        User user = userService.getById(userId);
        Context context = new Context();
        context.setVariable("name", user.getUserName());
        int code = (int)((Math.random()*9+1)*100000);
        String jjjj = Integer.toString(code);
//        String url = domain  + "/caigou/user/activation/" + user.getUserId() + "/" + code;
        context.setVariable("code", jjjj);
        String content = templateEngine.process("/modify", context);
        mailClient.setMail(user.getUserEmail(), "修改密码", content);
        model.addAttribute("code",code);
        return code;
    }
}
