package com.kenzie.appserver.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.service.model.Reservation;
import com.kenzie.capstone.service.model.ReservationData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheStore {

    private Cache<String, List<Reservation>> cache;

    public CacheStore(int expiry, TimeUnit timeUnit){
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiry, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public List<Reservation> get (String key){
        return cache.getIfPresent(key);
    }

    public void evict(String key){
        if(key != null){
            cache.invalidate(key);
        }
    }

    public void add(String key, List<Reservation> value){
        if(key != null && value != null){
            cache.put(key, value);
        }
    }
}
