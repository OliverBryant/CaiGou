package com.example.caigou.service.impl;

import com.example.caigou.entity.Views;
import com.example.caigou.mapper.ViewsMapper;
import com.example.caigou.service.IViewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
@Service
public class ViewsServiceImpl extends ServiceImpl<ViewsMapper, Views> implements IViewsService {

    @Autowired
    private ViewsMapper viewsMapper;

    @Override
    public void addRecord(int comId, int userId,String comTag) {
        Views views = new Views();
        views.setUserId(userId);
        views.setComId(comId);
        views.setComTag(comTag);
        viewsMapper.insert(views);
    }
}
