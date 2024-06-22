package com.chicong291002.CheckRule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private int ruleId;

    @Column(name = "rule_name")
    private String ruleName;

    @ElementCollection
    @CollectionTable(name = "rule_counter_data", joinColumns = @JoinColumn(name = "rule_id"))
    private List<CounterData> counterDataList;
}
