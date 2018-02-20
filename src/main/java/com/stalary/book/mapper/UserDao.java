package com.stalary.book.mapper;

import com.stalary.book.data.entity.User;
import com.stalary.book.utils.SystemUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author Stalary
 * @description
 * @date 17/02/2018
 */
@Mapper
public interface UserDao {

    String TABLE_NAME = "user";
    String ALL_FIELDS = " id, username, nickname, password, salt, mail, createTime, updateTime, status";
    String INSERT_FIELDS = " username, nickname, password, salt, mail, createTime, updateTime";
    /**
     * 搜索所有用户
     * @return 返回用户list
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "status >= 0"})
    List<User> findAll();

    /**
     * 插入一个用户
     * @param user 对象
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") values (#{username},#{nickname},#{password},#{salt},#{mail},#{createTime},#{updateTime})"})
    void save(User user);

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return User
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "username=#{username}", SystemUtil.STATUS})
    User findByName(String username);

    /**
     * 修改密码
     * @param user 对象
     */
    @Update({SystemUtil.UPDATE, TABLE_NAME, " set password=#{password}, updateTime=#{updateTime}", SystemUtil.WHERE, "username=#{username}", SystemUtil.STATUS})
    void update(User user);

    @Cacheable("user")
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "id=#{id}", SystemUtil.STATUS})
    User findById(int id);
}
