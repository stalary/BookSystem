package com.stalary.book.mapper;

import com.stalary.book.data.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Stalary
 * @description
 * @date 17/02/2018
 */
@Mapper
public interface UserDao {

    String TABLE_NAME = "user";
    String ALL_FIELDS = " id, username, password, salt, createTime, updateTime, status";
    String INSERT_FIELDS = " username, password, salt, createTime, updateTime";

    @Select({"select ", ALL_FIELDS, " from ", TABLE_NAME})
    List<User> findAll();

    @Insert({"insert ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{username},#{password},#{salt},#{createTime},#{updateTime})"})
    void save(User user);
}
