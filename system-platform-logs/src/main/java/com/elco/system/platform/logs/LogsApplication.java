package com.elco.system.platform.logs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author kay
 * @date 2021/9/26
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LogsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogsApplication.class,args);
    }
}
