package com.template.users_service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    public void blacklistToken(String token, long expirationTime) {
        blacklist.put(token, expirationTime);
    }

    public boolean isTokenBlacklisted(String token) {
        Long exp = blacklist.get(token);
        if (exp == null)
            return false;
        if (exp < System.currentTimeMillis()) {
            blacklist.remove(token);
            return false;
        }
        return true;
    }

    @Scheduled(fixedDelay = 60000)
    public void cleanup() {
        long now = System.currentTimeMillis();
        blacklist.entrySet().removeIf(e -> e.getValue() < now);
    }
}