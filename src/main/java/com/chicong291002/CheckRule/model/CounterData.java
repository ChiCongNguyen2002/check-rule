package com.chicong291002.CheckRule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CounterData {
    @Column(name = "counter_key")
    private String counterKey;

    @Column(name = "limitValue")
    private int limitValue;
}

