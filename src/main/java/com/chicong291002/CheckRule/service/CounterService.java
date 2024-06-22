package com.chicong291002.CheckRule.service;

public interface CounterService {
    boolean checkCounter(Integer userId, Integer ruleId);
    boolean checkAndUpdateCounter(Integer userId, Integer ruleId);
}
