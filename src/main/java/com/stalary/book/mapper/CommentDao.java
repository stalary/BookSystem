package com.stalary.book.mapper;

import com.stalary.book.data.entity.Comment;
import com.stalary.book.utils.SystemUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Stalary
 * @description
 * @date 19/02/2018
 */
@Mapper
public interface CommentDao {

    String TABLE_NAME = "comment";
    String ALL_FIELDS = " id, content, score, userId, bookId, createTime, updateTime, status";
    String INSERT_FIELDS = " content, score, userId, bookId, createTime, updateTime";

    /**
     * 保存一条评论
     * @param comment
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") values (#{content},#{score},#{userId},#{bookId},#{createTime},#{updateTime})"})
    void save(Comment comment);

    /**
     * 通过图书id查找评论
     * @param bookId
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "bookId=#{bookId}", SystemUtil.STATUS})
    List<Comment> findByBookId(int bookId);
}
