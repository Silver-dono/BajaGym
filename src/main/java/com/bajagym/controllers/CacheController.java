package com.bajagym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CacheController {

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(value="/cache1", method= RequestMethod.GET)
    public Map<Object, Object> getCache1Content() {
        ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
        ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("RutinasEj");
        return cache.getNativeCache();
    }

    @RequestMapping(value="/cache2", method= RequestMethod.GET)
    public Map<Object, Object> getCache2Content() {
        ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
        ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("ClasesColectivas");
        return cache.getNativeCache();
    }

}
