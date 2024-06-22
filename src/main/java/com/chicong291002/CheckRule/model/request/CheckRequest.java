package com.chicong291002.CheckRule.model.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckRequest {
    private Integer userId;
    private Integer ruleId;
}
