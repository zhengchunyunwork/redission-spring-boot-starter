package com.zcy.learn.redission;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
@Configuration
public class RedissonAutoConfig {

    @Bean
    public RedissonClient configRedissonClient(RedissonProperties redissonProperties){
        Config config = new Config();
        String prifix = "redis://";
        if (redissonProperties.isSsl()) {
            prifix = "rediss://";
        }
        config.useSingleServer().setAddress(prifix + redissonProperties.getHost() + ":" + redissonProperties.getPort())
                .setConnectTimeout(redissonProperties.getTimeout());
        return Redisson.create(config);
    }
}
