package com.chicong291002.CheckRule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "counter_id")
    private int counterId;

    @Column(name = "counter_name")
    private String counterName;

    @Column(name = "counter_value")
    private int counterValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private Rule rule;
}
