package com.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



/**
 * @author mrliz
 * XXX.properties配置文件手动加载类
 * @Configuration 指定是一个配置类
 * @PropertySource("classpath:xxxx.properties") 指定需要加载的配置文件路径
 */
@Configuration
@PropertySource("classpath:sysconfig.properties")
public class SysConfig {
}
