package com.stalary.book.utils;

import java.util.concurrent.*;

/**
 * SystemUtil
 *
 * 存放全局变量等
 * @author lirongqian
 * @since 2018/02/09
 */
public class SystemUtil {

    public static final String INSERT = "insert into ";

    public static final String UPDATE = "update ";

    public static final String DELETE = "delete from ";

    public static final String SELECT = "select ";

    public static final String FROM = " from ";

    public static final String WHERE = " where ";

    public static final String STATUS = " and status >= 0 ";

    public static final String BOOK = "book";

    public static final String COVER = "cover";

    public static final String SPLIT = ":";

    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1000);

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    static {
        consumer();
    }

    public static void consumer() {
        System.out.println("启动消费者");
        executor.submit(() -> {
            while (true) {
                if (queue.size() != 0) {
                    System.out.println(queue.poll());
                }
            }
        });
    }
}