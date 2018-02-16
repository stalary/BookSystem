package com.stalary.book.data.entity;

import com.stalary.book.annotation.CreateTime;
import com.stalary.book.annotation.UpdateTime;
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
    @CreateTime
    private Date createTime;

    /**
     * 更新时间
     */
    @UpdateTime
    private Date updateTime;

    /**
     * 状态 0代表正常，-1代表删除
     */
    private int status = 0;
}