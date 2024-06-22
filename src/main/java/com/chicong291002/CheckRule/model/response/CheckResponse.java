package com.chicong291002.CheckRule.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckResponse {
    private Integer userId;
    private Integer ruleId;
    private boolean pass;
}
