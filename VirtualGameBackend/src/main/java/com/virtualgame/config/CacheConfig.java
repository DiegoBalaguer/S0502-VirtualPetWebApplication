package com.virtualgame.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        log.info("Configuring cache manager...");

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        cacheManager.registerCustomCache("appSettings",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterAccess(90, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache("pets",
                Caffeine.newBuilder()
                        .maximumSize(1000)
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache("petAction",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterAccess(30, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache("petHabitat",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterAccess(30, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache("petHabitatsAll",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterAccess(30, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache("petHabitatsByParentIdAll",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterAccess(30, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache("pet",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterAccess(90, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache("userEntity",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterAccess(30, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache("petsUser",
                Caffeine.newBuilder()
                        .maximumSize(200)
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .recordStats()
                        .build());

        return cacheManager;
    }
}