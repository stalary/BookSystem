/**
 * @(#)BookRepo.java, 2018-02-09.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.repository;

import com.stalary.book.data.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BookRepo
 *
 * @author lirongqian
 * @since 2018/02/09
 */
public interface BookRepo extends JpaRepository<Book, Integer>{
}