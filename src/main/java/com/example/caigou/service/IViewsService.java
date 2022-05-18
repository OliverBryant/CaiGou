package com.example.caigou.service;

import com.example.caigou.entity.Views;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
public interface IViewsService extends IService<Views> {
    public void addRecord(int comId,int userId,String comTag);
}
