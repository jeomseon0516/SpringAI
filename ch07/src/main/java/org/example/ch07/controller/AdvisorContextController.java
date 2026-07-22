package org.example.ch07.controller;

import lombok.RequiredArgsConstructor;
import org.example.ch07.service.AdvisorContextService;
import org.example.ch07.service.AdvisorSafeGuardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AdvisorContextController {

    private final AdvisorContextService service;

    @GetMapping("/ai/advisor-context")
    public String advisorContext() {
        return "advisor-context";
    }

    @ResponseBody
    @PostMapping("/ai/advisor-context")
    public String advisorContext(@RequestParam("question") String question) {
        return service.call(question);
    }
}
