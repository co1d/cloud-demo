package com.zplus.cloudauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.zplus.cloudauth.dao")
@EnableDiscoveryClient
public class CloudAuthApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CloudAuthApplication.class, args);
    }

}
