package com.example.caigou.service;

import com.example.caigou.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
public interface IUserService extends IService<User> {

    public Map<String, Object> userLogin(int userId, String userPassword);

    public Map<String, Object> userRegister(int userId, String userName, String userNick, String userPassword, String userDepartment, String userClass, String userEmail);

    public Map<String, Object> userInfo(int userId);

    public Map<String, Object> uploadToOSS(String fileName, String imgFilePath,String type,int userId) throws FileNotFoundException;

//    public Map<String, Object> reversePassword(int userId, String oldPassword, String newPassword);

}
