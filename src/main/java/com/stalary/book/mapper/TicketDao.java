package com.stalary.book.mapper;

import com.stalary.book.data.entity.Ticket;
import com.stalary.book.utils.SystemUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Stalary
 * @description
 * @date 17/02/2018
 */
@Mapper
public interface TicketDao {

    String TABLE_NAME = "ticket";
    String ALL_FIELDS = " id, userId, ticket, expired, status";
    String INSERT_FIELDS = " userId, ticket, expired, createTime, updateTime";

    /**
     * 保存
     * @param ticket
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},#{ticket},#{expired},#{createTime},#{updateTime})"})
    void save(Ticket ticket);

    /**
     * 查找用户的Ticket
     * @param userId
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "userId=#{userId}", SystemUtil.STATUS})
    Ticket findByUser(int userId);

    /**
     * 更新Ticket状态
     * @param ticket
     */
    @Update({SystemUtil.UPDATE, TABLE_NAME, " set status=#{status} ", SystemUtil.WHERE, "userId=#{userId}"})
    void updateStatus(Ticket ticket);

    /**
     * 更新Ticket作废时间
     * @param ticket
     */
    @Update({SystemUtil.UPDATE, TABLE_NAME, " set expired=#{expired} and updateTime=#{updateTime}", SystemUtil.WHERE, "userId=#{userId}"})
    void updateExpired(Ticket ticket);

}
