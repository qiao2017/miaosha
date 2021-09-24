package com.example.demo.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "jedis")
@Component
public class JedisConfig {
    private String host;
    private Integer port;
}
