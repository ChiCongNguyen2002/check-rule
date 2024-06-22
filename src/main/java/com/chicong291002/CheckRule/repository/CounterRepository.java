package com.chicong291002.CheckRule.repository;

import com.chicong291002.CheckRule.model.Counter;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Counter c WHERE c.user.userId = :userId AND c.rule.ruleId = :ruleId")
    void findByRuleIdAndUserIdForUpdate(@Param("userId") Integer userId, @Param("ruleId") Integer ruleId);

    @Query("SELECT EXISTS (SELECT 1 FROM Counter c WHERE c.rule.ruleId = :ruleId AND c.user.userId = :userId AND c.counterValue >= :limitValue)")
    boolean existsLimitExceeded(@Param("ruleId") Integer ruleId, @Param("userId") Integer userId, @Param("limitValue") int limitValue);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Counter (counter_name, counter_value, user_id, rule_id) VALUES (:counterName, 1, :userId, :ruleId) " +
            "ON DUPLICATE KEY UPDATE counter_value = counter_value + 1", nativeQuery = true)
    void upsertCounter(@Param("ruleId") Integer ruleId, @Param("userId") Integer userId, @Param("counterName") String counterName);
}
