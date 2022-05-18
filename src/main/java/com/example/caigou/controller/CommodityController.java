package com.example.caigou.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.caigou.entity.Commodity;
import com.example.caigou.entity.User;
import com.example.caigou.service.impl.CommodityServiceImpl;
import com.example.caigou.service.impl.UserServiceImpl;
import com.example.caigou.service.impl.ViewsServiceImpl;
import com.example.caigou.unit.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
@Controller
@RequestMapping("/caigou/commodity")
public class CommodityController {

    @Autowired
    private CommodityServiceImpl commodityService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ViewsServiceImpl viewsService;

    @RequestMapping(path = "/release", method = RequestMethod.GET)
    public String getReleasePage() {
        return "release";
    }

    @RequestMapping(path = "/release", method = RequestMethod.POST)
    public String releaseCommodity(MultipartFile img01, MultipartFile img02, MultipartFile img03, MultipartFile img04, String title, String describe, Double price, String product_type, Model model, HttpSession session) {

        String str = "";
        JSONObject json = new JSONObject();
        Object[] obj = new Object[4];
        int length = 1;
        obj[0] = img01;
        if (img02 != null) {
            obj[1] = img02;
            length += 1;
            if (img03 != null) {
                obj[2] = img03;
                length += 1;
                if (img04 != null) {
                    obj[3] = img04;
                    length += 1;
                }
            }
        }
        try {
            while (length-- != 0) {
                MultipartFile temp = (MultipartFile) obj[length];
                String suffix = temp.getContentType().toLowerCase();//图片后缀，用以识别哪种格式数据
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
                    temp.transferTo(targetFile);

                    model.addAttribute("success", "上传图片成功");

                    Map<String, Object> map = userService.uploadToOSS(fileName, imgFilePath, "commodityPic", (int) session.getAttribute("loginUser"));
                    if (map.containsKey("success")) {
                        if (length == 0) {
                            str = (String) map.get("url") + str;
                        } else {
                            str = "," + (String) map.get("url") + str;
                        }

                    } else {
                        String Msg = (String) map.get("MSG");
                        System.out.println(Msg);
                    }
                    System.out.println(map.get("url"));
                }
                model.addAttribute("MSG", "系统异常，上传图片格式非法");
            }
        } catch (Exception e) {
            model.addAttribute("系统异常，上传图片失败");
        }


        String s = commodityService.releaseCommodity(str, title, describe, price, product_type, (int) session.getAttribute("loginUser"));
        System.out.println(str);
        System.out.println(title);
        System.out.println(describe);
        System.out.println(price);
        System.out.println(product_type);
        return "redirect:/caigou/index";
    }

    @RequestMapping(path = "/product", method = RequestMethod.GET)
    public String selectOneProduct(int comId, Model model, HttpSession session) {
        Commodity commodity = commodityService.product(comId);
        List<Map<String, Object>> imageList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("image", commodity.getComImage());
        imageList.add(map);
        if (commodity.getComPic2() != null) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", commodity.getComPic2());
            imageList.add(map1);
            if (commodity.getComPic3() != null) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("image", commodity.getComPic3());
                imageList.add(map2);
                if (commodity.getComPic4() != null) {
                    Map<String, Object> map3 = new HashMap<>();
                    map3.put("image", commodity.getComPic4());
                    imageList.add(map3);
                }
            }
        }
        System.out.println(map.size());
        User user = userService.getById(commodity.getUserId());
        List<Map<String, Object>> list = new ArrayList<>();
        int i = user.getUserStar();
        while (i-- != 0) {
            list.add(map);
        }
        System.out.println("maps+" + list.size());
        model.addAttribute("main_img", commodity.getComImage());
        model.addAttribute("picture", imageList);
        model.addAttribute("commodity", commodity);
        model.addAttribute("email", user.getUserEmail());
        model.addAttribute("suibian", list);

        if (session.getAttribute("loginUser") != null)
            viewsService.addRecord(comId, (int) session.getAttribute("loginUser"), commodity.getComTag());

        return "product";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String getAdminPage(Model model,Page page) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("com_status",0,1);
        List<Map<String,Object>> list = commodityService.page(queryWrapper,page.getCurrent());
        page.setLimit(9);
        page.setPath("/caigou/commodity/admin");
        page.setRows((int) commodityService.count(queryWrapper));
        model.addAttribute("commodity",list);
        return "/administrator";
    }

    @RequestMapping(path = "/verify",method = RequestMethod.GET)
    public String administrator(String[] comId,String state){
        for (int i = 0; i < comId.length; i++) {
            System.out.println(comId[i]);
            UpdateWrapper<Commodity> updateWrapper = new UpdateWrapper<>();
            if(state.equals("审核通过")){
                updateWrapper
                        .eq("com_id",comId[i])
                        .set("com_status",2);
            } else if(state.equals("驳回选择")){
                updateWrapper
                        .eq("com_id",comId[i])
                        .set("com_status",1);
            }
            commodityService.update(updateWrapper);
        }
        return "redirect:/caigou/commodity/admin";
    }

    @RequestMapping(path = "/view",method = RequestMethod.GET)
    public String getAdministratorView(Model model, Page page){
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_status",2);
        List<Map<String,Object>> list = commodityService.page(queryWrapper,page.getCurrent());
        page.setLimit(9);
        page.setPath("/caigou/commodity/view");
        page.setRows((int) commodityService.count(queryWrapper));

        System.out.println(list);
        model.addAttribute("commodity",list);
        return "censor";
    }

    @RequestMapping(path = "/down",method = RequestMethod.GET)
    public String down(String[] comId,String state){
        for (int i = 0; i < comId.length; i++) {
            System.out.println(comId[i]);
            UpdateWrapper<Commodity> updateWrapper = new UpdateWrapper<>();
            if(state.equals("暂时下架")){
                updateWrapper
                        .eq("com_id",comId[i])
                        .set("com_status",1);
                commodityService.update(updateWrapper);
            } else if(state.equals("删除商品")){
                commodityService.removeById(comId[i]);

            }
        }
        return "redirect:/caigou/commodity/view";
    }
}
