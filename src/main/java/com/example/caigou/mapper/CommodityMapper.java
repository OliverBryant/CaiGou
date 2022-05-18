package com.example.caigou.mapper;

import com.example.caigou.entity.Commodity;
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
public interface CommodityMapper extends BaseMapper<Commodity> {

    @Select("select com_id,com_name,com_image,com_price from commodity as c join user u on c.user_id = u.user_id where c.com_status=2 and u.user_department = #{userDepartment} order by com_view desc;")
    public List<Map<String, Object>> departmentSuggesstion(String userDepartment);

    /*@Select("select * from commodity as c\n" +
            "join (\n" +
            "    select v.com_tag from views as v\n" +
            "    where v.user_id = #{userId} \n" +
            "    order by views_time desc limit #{many},1\n" +
            "    ) as b on c.com_tag = b.com_tag\n" +
            "order by c.com_view desc\n" +
            "limit 0,2;")
    public List<Map<String, Object>> guessYouLike(int userId,int many);*/

    @Select("select *\n" +
            "from commodity as c\n" +
            "         where c.com_tag = #{comTag}\n" +
            "order by c.com_view desc\n" +
            "limit 0,#{num};")
    public List<Map<String, Object>> guessYouLike(String comTag,int num);

    @Select("select distinct c.com_tag from commodity as c join (\n" +
            "    select v.com_tag\n" +
            "    from secondhut.views as v\n" +
            "    where v.user_id = #{userId}\n" +
            "    order by views_time desc\n" +
            "    limit 4\n" +
            ") as b on b.com_tag = c.com_tag\n" +
            "order by c.com_view desc;")
    public List<String> guessYouLikeTag(int userId);
}
