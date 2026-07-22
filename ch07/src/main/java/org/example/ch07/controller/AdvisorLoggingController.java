package org.example.ch07.controller;

import lombok.RequiredArgsConstructor;
import org.example.ch07.service.AdvisorLoggingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AdvisorLoggingController {

    private final AdvisorLoggingService service;

    @GetMapping("/ai/advisor-logging")
    public String advisorLogging() {
        return "advisor-logging";
    }

    @ResponseBody
    @PostMapping("/ai/advisor-logging")
    public String advisorLogging(@RequestParam("question") String question) {
        return service.call(question);
    }
}
