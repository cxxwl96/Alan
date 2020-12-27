package com.alan.component.thymeleaf.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.alan.component.thymeleaf.AlanDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cxxwl96@sina.com
 * @date 2020/8/14
 */
@Configuration
public class ThymeleafConfig {

    /**
     * 配置自定义的CusDialect，用于整合thymeleaf模板
     */
    @Bean
    public AlanDialect getalanDialect() {
        return new AlanDialect();
    }

    /**
     * 配置shiro扩展标签，用于控制权限按钮的显示
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
