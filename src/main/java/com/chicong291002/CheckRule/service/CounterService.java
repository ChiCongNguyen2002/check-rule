package com.chicong291002.CheckRule.service;

public interface CounterService {
    boolean checkCounter(Long userId, Long ruleId);
    boolean checkAndUpdateCounter(Long userId, Long ruleId);
}
