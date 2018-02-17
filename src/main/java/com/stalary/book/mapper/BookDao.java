package com.stalary.book.mapper;

import com.stalary.book.data.entity.Book;
import com.stalary.book.utils.SystemUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookDao {

    String TABLE_NAME = "book";
    String ALL_FIELDS = " id, bookName, userId, coverUrl, pdfUrl, downloadCount, score, createTime, updateTime, status";
    String INSERT_FIELDS = " bookName, userId, coverUrl, pdfUrl, createTime, updateTime";

    /**
     * 获得所有图书，按照上传时间和分数降序排序
     *
     * @return 图书List
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "status >= 0",
            " ORDER BY createTime DESC,score DESC"})
    List<Book> findAll();

    /**
     * 插入一本书
     *
     * @param book Book对象
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") VALUES(#{bookName},#{userId},#{coverUrl},#{pdfUrl},",
            "#{createTime},#{updateTime})"})
    void save(Book book);

    /**
     * 根据用户id获得其上传的所有图书，按照上传时间和分数降序排序
     *
     * @param userId 用户id
     * @return 图书List
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "userId=#{userId}",
            SystemUtil.STATUS, " ORDER BY createTime DESC,score DESC"})
    List<Book> findByUserId(int userId);
}
