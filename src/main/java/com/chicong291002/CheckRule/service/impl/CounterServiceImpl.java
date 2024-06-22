package com.chicong291002.CheckRule.service.impl;

import com.chicong291002.CheckRule.model.CounterData;
import com.chicong291002.CheckRule.model.Rule;
import com.chicong291002.CheckRule.repository.CounterRepository;
import com.chicong291002.CheckRule.repository.RuleRepository;
import com.chicong291002.CheckRule.repository.UserRepository;
import com.chicong291002.CheckRule.service.CounterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterRepository counterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RuleRepository ruleRepository;

    @Override
    public boolean checkCounter(Integer userId, Integer ruleId) {
        Rule rule = ruleRepository.findById(ruleId).orElseThrow(() -> new RuntimeException("Rule not found"));
        for(CounterData counterData : rule.getCounterDataList()){
            boolean checkLimit = counterRepository.existsLimitExceeded(userId,ruleId,counterData.getLimitValue());
            if (checkLimit) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean checkAndUpdateCounter(Integer userId, Integer ruleId) {
        Rule rule = ruleRepository.findById(ruleId).orElseThrow(() -> new RuntimeException("Rule not found"));

        for(CounterData counterData : rule.getCounterDataList()){
            boolean checkLimit = counterRepository.existsLimitExceeded(userId,ruleId,counterData.getLimitValue());
            if (checkLimit) {
                return false;
            }
        }

        // Use pessimistic locking to ensure consistency
        counterRepository.findByRuleIdAndUserIdForUpdate(userId, ruleId);

        // Use insert on duplicate key update
        for (CounterData counterData : rule.getCounterDataList()) {
            counterRepository.upsertCounter(ruleId, userId, counterData.getCounterKey());
        }

        return true;
    }
}
