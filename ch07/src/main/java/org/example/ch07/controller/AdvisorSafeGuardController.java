package org.example.ch07.controller;

import lombok.RequiredArgsConstructor;
import org.example.ch07.service.AdvisorLoggingService;
import org.example.ch07.service.AdvisorSafeGuardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AdvisorSafeGuardController {

    private final AdvisorSafeGuardService service;

    @GetMapping("/ai/advisor-safe-guard")
    public String advisorSafeGuard() {
        return "advisor-safe-guard";
    }

    @ResponseBody
    @PostMapping("/ai/advisor-safe-guard")
    public String advisorSafeGuard(@RequestParam("question") String question) {
        return service.call(question);
    }
}
