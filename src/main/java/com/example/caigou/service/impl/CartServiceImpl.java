package com.example.caigou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.caigou.entity.Cart;
import com.example.caigou.mapper.CartMapper;
import com.example.caigou.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.caigou.unit.ConstantNumUnit;
import lombok.AllArgsConstructor;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public void addToCart(int comId, int userId) {
        Cart cart = new Cart();
        cart.setComId(comId);
        cart.setUserId(userId);
        cartMapper.insert(cart);
    }

    @Override
    public List<Map<String, Object>> myCart(int userId) {
        List<Map<String,Object>> list = cartMapper.myCart(userId);
        return list;
    }

    @Override
    public void delete(int comId, int userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("user_id",userId)
                        .eq("com_id",comId);
        cartMapper.delete(queryWrapper);
    }
}
