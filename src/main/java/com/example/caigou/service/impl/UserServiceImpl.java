package com.example.caigou.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.caigou.entity.MailClient;
import com.example.caigou.entity.User;
import com.example.caigou.mapper.UserMapper;
import com.example.caigou.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${shop.path.domain}")
    String domain;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @Override
    public Map<String, Object> userLogin(int userId, String userPassword) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (Integer.toString(userId).length() == 10) {
            queryWrapper.eq("user_id", userId);
        } else{
            map.put("Msg", "该账户不存在");
            return map;
        }

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            map.put("Msg", "该账户不存在");
            return map;
        }

        if (!user.getUserPassword().equals(userPassword)) {
            map.put("Msg", "密码不正确");
            return map;
        }
        map.put("success", "1");
        map.put("user", user);
        return map;
    }

    @Override
    public Map<String, Object> userRegister(int userId, String userName, String userNick, String userPassword, String userDepartment, String userClass, String userEmail) {
        Map<String, Object> map = new HashMap<>();

        System.out.println(userName);

        User user = userMapper.selectById(userId);
        System.out.println(user);
        if (!userName.equals(user.getUserName())) {
            map.put("MSG", "请检查学号姓名是否匹配！");
            return map;
        }

        user.setUserNick(userNick);
        user.setUserEmail(userEmail);
        user.setUserDepartment(userDepartment);
        user.setUserPassword(userPassword);
        user.setUserStatus(3);
        user.setUserClass(userClass);
        System.out.println(user.toString());

        System.out.println(userEmail);

        //        发送激活邮件
        Context context = new Context();
        context.setVariable("name", userName);
        int code = userId/3%99999;
        String url = domain  + "/caigou/user/activation/" + user.getUserId() + "/" + code;
        context.setVariable("url", url);
        String content = templateEngine.process("/activation", context);
        mailClient.setMail(userEmail, "激活账户", content);

        userMapper.updateById(user);

        map.put("success", "1");

        return map;
    }

    @Override
    public Map<String, Object> userInfo(int userId) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.selectById(userId);
        if (user == null) {
            map.put("userMsg", "没有查到相关用户信息");
            return map;
        }
        map.put("success", "1");
        map.put("user", user);
        return map;
    }

    @Override
    public Map<String, Object> uploadToOSS(String fileName, String imgFilePath,String type,int userId) throws FileNotFoundException {

        Map<String,Object> map = new HashMap<>();

        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tA1kmGaDr4JBviyr9eg";
        String accessKeySecret = "51KSlSvheGZGm1JbbFPh9QDI5P4SVx";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        InputStream inputStream = new FileInputStream(imgFilePath + fileName);

        String folder;
        if(type.equals("userPic")){
            folder="picture/";
        }else if (type.equals("commodityPic")){
            folder="CommodityPicture/";
        }else {
            map.put("MSG","请确认传输图片的种类");
            return map;
        }

        ossClient.putObject("caigoufun", folder + fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        String url;
        url="https://caigoufun.oss-cn-qingdao.aliyuncs.com/"+folder+fileName;

        map.put("success","1");
        map.put("url",url);
        return map;
    }

    /*@Override
    public Map<String, Object> reversePassword(int userId, String oldPassword, String newPassword){
        Map<String,Object> map = new HashMap<>();

        User user = userMapper.selectById(userId);

        System.out.println(user.getUserPassword());
        System.out.println(oldPassword+"     "+newPassword);

        if(!user.getUserPassword().equals(oldPassword)){
            map.put("MSG","原密码输入错误");
            return map;
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper
//                .eq("user_id",userId)
                .set("user_password",newPassword);
        userMapper.update(user,updateWrapper);

        map.put("success","1");
        return map;

    }*/

}
