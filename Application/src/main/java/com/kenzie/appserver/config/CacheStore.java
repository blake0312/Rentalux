package com.kenzie.appserver.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.capstone.service.model.ReservationData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheStore {

    private Cache<String, List<ReservationData>> cache;

    public CacheStore(int expiry, TimeUnit timeUnit){
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiry, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public List<ReservationData> get (String key){
        return cache.getIfPresent(key);
    }

    public void evict(String key){
        if(key != null){
            cache.invalidate(key);
        }
    }

    public void add(String key, List<ReservationData> value){
        if(key != null && value != null){
            cache.put(key, value);
        }
    }
}
