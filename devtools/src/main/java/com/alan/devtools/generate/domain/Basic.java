package com.alan.devtools.generate.domain;

import lombok.Data;

/**
 * @author cxxwl96@sina.com
 * @date 2020/10/21
 */
@Data
public class Basic {
    private String projectPath;
    private String packagePath;
    private String author;
    private String genTitle;
    private String genModule;
    private String genGroup;
    private Long genPMenu;
    private String tablePrefix;
    private String tableName;
    private String tableEntity;
    private String requestMapping;
    private Integer moduleType;
}
