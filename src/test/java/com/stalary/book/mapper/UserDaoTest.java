package com.stalary.book.mapper;

import com.stalary.book.data.entity.User;
import com.stalary.book.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Stalary
 * @description
 * @date 17/02/2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Sql("/init.sql")
@SpringBootTest
@Slf4j
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void save() {

    }
}
