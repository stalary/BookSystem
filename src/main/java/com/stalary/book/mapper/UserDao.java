package com.stalary.book.mapper;

import com.stalary.book.data.entity.User;
import com.stalary.book.utils.SystemUtil;
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

    /**
     * 搜索所有用户
     * @return 返回用户list
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME})
    List<User> findAll();

    /**
     * 插入一个用户
     * @param user 对象
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") values (#{username},#{password},#{salt},#{createTime},#{updateTime})"})
    void save(User user);

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return User
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, " from ", TABLE_NAME, " where username=#{username} and status >= 0"})
    User findByName(String username);
}
