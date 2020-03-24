//--------------------------------------------------------------------
// 日期：2019/7/16 11:15
// 人员：mrliz
// 原因：
//--------------------------------------------------------------------

package com.springboot.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 * @author mrliz
 */
@Configuration
@EnableAsync
public class TreadPoolConfig {


    @Bean("scanBussinessFile")
    public ThreadPoolTaskExecutor scanBussinessFile(){

        ThreadPoolTaskExecutor executor =new ThreadPoolTaskExecutor();

        // 核心线程数（默认线程数）
        executor.setCorePoolSize(20);

        // 最大线程数
        executor.setMaxPoolSize(30);

        // 缓冲队列数
        executor.setQueueCapacity(100);

        // 允许线程空闲时间（单位：默认为秒）
        executor.setKeepAliveSeconds(10);

        // 线程池名前缀
        executor.setThreadNamePrefix("scanBussinessFile-");

        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    @Bean("uploadBlockFile")
    public ThreadPoolTaskExecutor uploadBlockFile(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("uploadBlockFile-");

        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }


}
