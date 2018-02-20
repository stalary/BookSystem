package com.stalary.book.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Ticket
 *
 * @author lirongqian
 * @since 2018/02/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends BaseEntity {

    /**
     * 关联的用户id
     */
    private int userId;

    /**
     * 编码
     */
    private String ticket;

    /**
     * 到期时间
     */
    private Date expired;
}