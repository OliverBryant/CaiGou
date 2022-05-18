package com.example.caigou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "com_id", type = IdType.AUTO)
    private Integer comId;

    private String comImage;

    private String comName;

    private String comInfo;

    private Double comPrice;

    private Integer comNum;

    private String comTag;

    private Integer comView;

    private Integer comStatus;

    private Integer userId;

    private String comPic2;

    private String comPic3;

    private String comPic4;
}
