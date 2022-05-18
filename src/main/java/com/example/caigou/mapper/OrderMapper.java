package com.example.caigou.mapper;

import com.example.caigou.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
public interface OrderMapper extends BaseMapper<Orders>{

    @Select("select * from commodity where com_id in (select com_id from cart where cart.user_id = #{userId})")
    public List<Map<String,Object>> myOrder(int userId);


}
