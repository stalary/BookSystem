package com.stalary.book.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Page
 *
 * @author lirongqian
 * @since 2018/02/19
 */
@Data
@NoArgsConstructor
public class Page {

    private int pageIndex;

    private int pageSize;

    private int start;

    public Page(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.start = (pageIndex - 1) * pageSize;
    }

}