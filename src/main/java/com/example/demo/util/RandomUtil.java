package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtil {
    private final Random random = new Random();
    public synchronized Boolean nextBoolean(){
        return random.nextBoolean();
    }
}
