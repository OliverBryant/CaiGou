package com.example.caigou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String userImage;

    private String userNick;

    private String userName;

    private String userEmail;

    private String userPassword;

    private String userDepartment;

    private String userClass;

    private Integer userStatus;

    private Integer userStar;


}
