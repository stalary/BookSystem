package com.stalary.book.data.entity;

import lombok.Data;

import java.util.Date;

/**
 * BaseEntity
 *
 * @author lirongqian
 * @since 2018/02/17
 */
@Data
abstract class BaseEntity {

    /**
     * 自增id
     */
    private int id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态 0代表正常，-1代表删除
     */
    private int status = 0;
}