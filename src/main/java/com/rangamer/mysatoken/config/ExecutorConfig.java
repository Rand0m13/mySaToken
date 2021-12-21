package com.rangamer.mysatoken.config;

import cn.hutool.core.thread.ExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;

/**
 * @author jesse
 * @date 2021年12月20日 10:59
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean("upThread")
    public ExecutorService upExecutor() {
        //获取当前机器的核数
        int cpuNum = Runtime.getRuntime().availableProcessors();
        return ExecutorBuilder.create()
                .setCorePoolSize(cpuNum)
                .setMaxPoolSize(cpuNum * 2)
                .setKeepAliveTime(180000)
                .build();
    }
}
