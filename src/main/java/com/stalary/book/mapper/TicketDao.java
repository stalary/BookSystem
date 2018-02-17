package com.stalary.book.mapper;

import com.stalary.book.data.entity.Ticket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Stalary
 * @description
 * @date 17/02/2018
 */
@Mapper
public interface TicketDao {

    String TABLE_NAME = "ticket";
    String ALL_FIELDS = " id, user_id, ticket, expired, status";
    String INSERT_FIELDS = " user_id, ticket, expired, createTime, updateTime";

    @Insert({"insert into"})
    void save(Ticket ticket);

    @Select({})
    Ticket findByUser(int userId);

    @Insert({})
    void update(Ticket ticket);

}
