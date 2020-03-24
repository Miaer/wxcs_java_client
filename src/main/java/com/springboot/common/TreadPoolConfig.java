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

@Configuration
@EnableAsync
public class TreadPoolConfig {
    private static final int corePoolSize = 10;       		// 核心线程数（默认线程数）
    private static final int maxPoolSize = 200;			    // 最大线程数
    private static final int keepAliveTime = 10;			// 允许线程空闲时间（单位：默认为秒）
    private static final int queueCapacity = 200;			// 缓冲队列数
    private static final String threadNamePrefix1 = "Async-Service1-"; // 线程池名前缀
    private static final String threadNamePrefix2 = "Async-Service2-"; // 线程池名前缀

    @Bean("scanBussinessFile") // 默认为首字母小写的方法名
    public ThreadPoolTaskExecutor scanBussinessFile(){
        ThreadPoolTaskExecutor executor =new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("scanBussinessFile-");

        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    @Bean("uploadBlockFile") // bean的名称，默认为首字母小写的方法名
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
