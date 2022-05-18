package com.example.caigou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    private String orderAddress;

    private Integer buyerId;

    private String comId;

    private LocalDateTime orderTime;

    private Integer orderStatus;

    private Integer orderTotal;

}
