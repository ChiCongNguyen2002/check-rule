package com.chicong291002.CheckRule.controller;
import com.chicong291002.CheckRule.model.request.CheckRequest;
import com.chicong291002.CheckRule.model.response.CheckResponse;
import com.chicong291002.CheckRule.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CounterController {
    @Autowired
    private CounterService counterService;

    @GetMapping("/check")
    public ResponseEntity<?> checkCounter(@RequestParam Long userId, @RequestParam Long ruleId) {
        boolean pass = counterService.checkCounter(userId, ruleId);
        return ResponseEntity.ok(new CheckResponse(userId, ruleId, pass));
    }

    @PostMapping("/checkAndUpdate")
    public ResponseEntity<?> checkAndUpdateCounter(@RequestBody CheckRequest request) {
        boolean pass = counterService.checkAndUpdateCounter(request.getUserId(), request.getRuleId());
        return ResponseEntity.ok(new CheckResponse(request.getUserId(), request.getRuleId(), pass));
    }
}
