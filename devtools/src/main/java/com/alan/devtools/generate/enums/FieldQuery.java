package com.alan.devtools.generate.enums;

import lombok.Getter;

/**
 * 代码生成-字段查询方式
 * @author cxxwl96@sina.com
 * @date 2020/10/21
 */
@Getter
public enum FieldQuery {

    /**
     * 简单查询-精准查询
     */
    Exact(1, "精准查询"),
    /**
     * 简单查询-模糊查询
     */
    Like(2, "模糊查询");

    private Integer code;

    private String message;

    FieldQuery(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}