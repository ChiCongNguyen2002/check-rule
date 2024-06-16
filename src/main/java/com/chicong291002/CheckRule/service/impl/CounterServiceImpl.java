package com.chicong291002.CheckRule.service.impl;

import com.chicong291002.CheckRule.model.Counter;
import com.chicong291002.CheckRule.model.Rule;
import com.chicong291002.CheckRule.model.User;
import com.chicong291002.CheckRule.repository.CounterRepository;
import com.chicong291002.CheckRule.repository.RuleRepository;
import com.chicong291002.CheckRule.repository.UserRepository;
import com.chicong291002.CheckRule.service.CounterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Override
    @Transactional
    public boolean checkCounter(Long userId, Long ruleId) {
        User user = userRepository.findById(userId).orElse(null);
        Rule rule = ruleRepository.findById(ruleId).orElse(null);
        if (user != null && rule != null) {
            List<Counter> counters = counterRepository.findByRuleAndUserWithLock(rule, user);
            for (Counter counter : counters) {
                if (counter.getCount() >= counter.getLimit()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean checkAndUpdateCounter(Long userId, Long ruleId) {
        User user = userRepository.findById(userId).orElse(null);
        Rule rule = ruleRepository.findById(ruleId).orElse(null);
        if (user != null && rule != null) {
            List<Counter> counters = counterRepository.findByRuleAndUserWithLock(rule, user);
            int totalCount = counters.stream().mapToInt(Counter::getCount).sum();
            int totalLimit = counters.stream().mapToInt(Counter::getLimit).sum();

            if (totalCount >= totalLimit) {
                return false;
            }

            for (Counter counter : counters) {
                if (counter.getCount() < counter.getLimit()) {
                    counter.setCount(counter.getCount() + 1);
                    counterRepository.save(counter);
                    return true;
                }
            }
        }
        return false;
    }
}
