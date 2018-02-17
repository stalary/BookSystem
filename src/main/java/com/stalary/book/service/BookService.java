package com.stalary.book.service;

import com.stalary.book.data.entity.Book;
import com.stalary.book.mapper.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * BookService
 *
 * @author hawk
 * @since 2018/02/09
 */
@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public void saveBook(Book book) {
        bookDao.save(book);
    }
    /**
     * 获得上传图书的书名
     *
     * @param book
     * @return
     */
    public String getBookName(MultipartFile book) {
        int dotPos = book.getOriginalFilename().lastIndexOf(".");
        return book.getOriginalFilename().substring(0, dotPos);
    }

    public List<Book> findAll() {
        return bookDao.findAll();
    }
}
