package com.example.caigou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Oliver
 * @since 2021-10-09
 */
@Getter
@Setter
public class Views implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "views_id", type = IdType.AUTO)
    private Integer viewsId;

    private Integer userId;

    private Integer comId;

    private String comTag;

    private LocalDateTime viewsTime;


}
