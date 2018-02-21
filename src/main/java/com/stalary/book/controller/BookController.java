package com.stalary.book.controller;

import com.stalary.book.annotation.LoginRequired;
import com.stalary.book.data.ResponseMessage;
import com.stalary.book.data.dto.BookDto;
import com.stalary.book.data.entity.Book;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.service.BookService;
import com.stalary.book.service.DtoService;
import com.stalary.book.service.ManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BookController
 *
 * @author hawk
 * @since 2018/02/09
 */

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private BookService bookService;

    @Autowired
    private DtoService dtoService;

    @ApiOperation(value = "上传图书", notes = "若不传name，则默认为文件名")
    @LoginRequired
    @PostMapping("/books")
    public ResponseMessage uploadBook(
            @RequestParam("book") MultipartFile book,
            @RequestParam(required = false, defaultValue = "") String name) {
        managerService.upload(book, name);
        return ResponseMessage.successMessage("上传成功！");
    }

    @ApiOperation(value = "获取图书列表", notes = "pageIndex为第几页，pageSize为每页大小，不传默认获取前10项")
    @GetMapping("/books")
    public ResponseMessage getBookList(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        List<Book> bookList = bookService.findAll(pageIndex, pageSize);
        List<BookDto> bookDtoList = bookList
                .stream()
                .map(book -> dtoService.getBookDto(book))
                .collect(Collectors.toList());
        return ResponseMessage.successMessage(bookDtoList);
    }

    @ApiOperation("获取一本图书的详细信息和评论信息，传入图书id")
    @GetMapping("/books/{bookId}/info")
    public ResponseMessage getInfo(
            @PathVariable("bookId") int bookId) {
        return ResponseMessage.successMessage(managerService.getInfo(bookId));
    }

    @ApiOperation("下载时，获取图书的url，用于统计下载数量，传入图书id")
    @GetMapping("/books/{bookId}")
    public ResponseMessage downloadBook(
            @PathVariable("bookId") int bookId) {
        return ResponseMessage.successMessage(bookService.downloadBook(bookId));
    }

    @ApiOperation("查找用户上传的图书")
    @GetMapping("/user/books")
    @LoginRequired
    public ResponseMessage findByUser() {
        List<Book> bookList = bookService.findByUserId(UserContextHolder.get().getId());
        List<BookDto> bookDtoList = bookList
                .stream()
                .map(book -> dtoService.getBookDto(book))
                .collect(Collectors.toList());
        return ResponseMessage.successMessage(bookDtoList);
    }

    @ApiOperation("删除一本图书")
    @DeleteMapping("/books/{bookId}")
    @LoginRequired
    public ResponseMessage delete(
            @PathVariable("bookId") int bookId) {
        managerService.delete(bookId);
        return ResponseMessage.successMessage("删除成功");
    }

}
