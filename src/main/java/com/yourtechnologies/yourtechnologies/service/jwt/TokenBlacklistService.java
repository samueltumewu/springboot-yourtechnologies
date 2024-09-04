package com.yourtechnologies.yourtechnologies.service.jwt;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
    private final ConcurrentHashMap<String, Boolean> blacklist = new ConcurrentHashMap<>();

    public void blacklistToken(String token) {
        blacklist.put(token, true);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.containsKey(token);
    }
}
