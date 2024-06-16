package com.chicong291002.CheckRule.repository;

import com.chicong291002.CheckRule.model.Counter;
import com.chicong291002.CheckRule.model.Rule;
import com.chicong291002.CheckRule.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Counter c WHERE c.rule = :rule AND c.user = :user")
    List<Counter> findByRuleAndUserWithLock(@Param("rule") Rule rule, @Param("user") User user);
}
