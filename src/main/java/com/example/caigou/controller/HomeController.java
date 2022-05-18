package com.example.caigou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.caigou.entity.Commodity;
import com.example.caigou.entity.Views;
import com.example.caigou.service.impl.CommodityServiceImpl;
import com.example.caigou.service.impl.ViewsServiceImpl;
import com.example.caigou.unit.ConstantNumUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/caigou")
public class HomeController {

    @Autowired
    private CommodityServiceImpl commodityService;

    @Autowired
    private ViewsServiceImpl viewsService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getHomePage(HttpSession session, Model model) {
        model.addAttribute("img1", "https://caigoufun.oss-cn-qingdao.aliyuncs.com/default/QQ%E5%9B%BE%E7%89%8720211022143714.jpg");
        model.addAttribute("img2", "https://caigoufun.oss-cn-qingdao.aliyuncs.com/default/QQ%E5%9B%BE%E7%89%8720211022144047.jpg");
        model.addAttribute("img3", "https://caigoufun.oss-cn-qingdao.aliyuncs.com/default/QQ%E5%9B%BE%E7%89%8720211022144053.jpg");

//        List<Map<String, Object>> maps = new ArrayList<>();


        if (session.getAttribute("loginUser") == null) {
            List<Map<String, Object>> list = commodityService.suggestByView();
            model.addAttribute("commodity", list);
        } else {
            int userId = (int) session.getAttribute("loginUser");
            List<Map<String, Object>> maps = commodityService.myCommodity(userId);
            QueryWrapper<Views> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            long count = viewsService.count(queryWrapper);
            if (count > 0) {
                List<Map<String, Object>> map = commodityService.guessYouLike(userId);
                model.addAttribute("commodity", maps);
                model.addAttribute("guess", map);
            } else {
                List<Map<String, Object>> list = commodityService.suggestByView();
                model.addAttribute("commodity", maps);
                model.addAttribute("guess", list);
            }
        }

        return "home";
    }


    @RequestMapping(path = "/mall", method = RequestMethod.GET)
    public String getMallPage(Model model, HttpSession session) {
        session.removeAttribute("tag");
        session.removeAttribute("menu_order");
        session.setAttribute("tag", "all");
        session.setAttribute("sorting", "menu_order");
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_status", 2);

//        List<Map<String, Object>> maps = commodityService.listMaps(queryWrapper);
        List<Map<String, Object>> maps = commodityService.page(queryWrapper, 0);
        int total = commodityService.totalPage(queryWrapper, 0);
        List<Map<String, Object>> num = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("tt", i + 1);
            num.add(map);
        }
        model.addAttribute("commodity", maps);
        model.addAttribute("num", num);
        model.addAttribute("sorting", "menu_order");
        model.addAttribute("number", 0);
        return "mall";
    }

    /*@RequestMapping(path = "/search",method = RequestMethod.POST)
    public String searchCommodity(){

    }*/

    @RequestMapping(path = "/contact", method = RequestMethod.GET)
    public String getContactPage() {
        return "contact";
    }

    @RequestMapping(path = "/about", method = RequestMethod.GET)
    public String getAboutPage() {
        return "about";
    }

    @RequestMapping(path = "/classify", method = RequestMethod.GET)
    public String classify(String type, Model model, HttpSession session) {
        session.removeAttribute("tag");
        session.removeAttribute("menu_order");
        session.setAttribute("tag", type);
        session.setAttribute("sorting", "menu_order");
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("com_tag", type)
                .eq("com_status",2);

        List<Map<String, Object>> maps = commodityService.page(queryWrapper, 0);
        int total = commodityService.totalPage(queryWrapper, 0);
        if (session.getAttribute("page") != null)
            session.removeAttribute("page");
        session.setAttribute("page", 1);
        List<Map<String, Object>> num = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("tt", i + 1);
            num.add(map);
        }

//        List<Map<String, Object>> list = commodityService.listMaps(queryWrapper);
        model.addAttribute("commodity", maps);
        model.addAttribute("num", num);
        model.addAttribute("sorting", "menu_order");
        model.addAttribute("number", 0);
        return "mall";
    }

    @RequestMapping(path = "/sorting", method = RequestMethod.GET)
    public String sort(String pag, String number, String sorting_list, Model model, HttpSession session) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        int current = Integer.parseInt(number);
        if (pag.equals("previous")) {
            current = current - 1;
        } else if (pag.equals("next")) {
            current = current + 1;
        }
        System.out.println(sorting_list);
        System.out.println(session.getAttribute("tag"));
        if (session.getAttribute("tag").equals("all")) {
            if (sorting_list.equals("menu_order")) {
                queryWrapper
                        .orderByAsc("com_id")
                        .eq("com_status",2);
            } else if (sorting_list.equals("price")) {
                queryWrapper
                        .orderByAsc("com_price")
                        .eq("com_status",2);
            } else if (sorting_list.equals("price-desc")) {
                queryWrapper
                        .orderByDesc("com_price")
                        .eq("com_status",2);
            }
        } else {
            if (sorting_list.equals("menu_order")) {
                queryWrapper
                        .eq("com_tag", (String) session.getAttribute("tag"))
                        .orderByAsc("com_id")
                        .eq("com_status",2);
            } else if (sorting_list.equals("price")) {
                queryWrapper
                        .eq("com_tag", (String) session.getAttribute("tag"))
                        .orderByAsc("com_price")
                        .eq("com_status",2);
            } else if (sorting_list.equals("price-desc")) {
                queryWrapper
                        .eq("com_tag", (String) session.getAttribute("tag"))
                        .orderByDesc("com_price")
                        .eq("com_status",2);
            }
        }

        List<Map<String, Object>> maps = commodityService.page(queryWrapper, current);
        int total = commodityService.totalPage(queryWrapper, current);
        List<Map<String, Object>> num = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("tt", i + 1);
            num.add(map);
        }

//        List<Map<String, Object>> list = commodityService.listMaps(queryWrapper);
        model.addAttribute("commodity", maps);
        model.addAttribute("num", num);
        model.addAttribute("number", current);
        model.addAttribute("sorting",sorting_list);

        /*List<Map<String, Object>> list = commodityService.page(queryWrapper, 0);
        System.out.println(list.toString());
        model.addAttribute("commodity", list);*/
        return "mall";
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String research(String keyword, Model model) {
        model.addAttribute("commodity", commodityService.research(keyword, 0));
        return "mall";
    }
}
