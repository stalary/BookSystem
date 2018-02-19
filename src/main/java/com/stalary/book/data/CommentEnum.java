package com.stalary.book.data;

import lombok.Getter;

/**
 * @author Stalary
 * @description 评价枚举类，0-1分代表差评，2-3分代表中评，4-5分代表好评
 * @date 19/02/2018
 */
public enum CommentEnum {

    GOOD(1, "好评"),
    MEDIUM(2, "中评"),
    NEGATIC(3, "差评");

    CommentEnum(int id, String type) {
        this.id = id;
        this.type = type;
    }

    @Getter
    private int id;

    @Getter
    private String type;
}
