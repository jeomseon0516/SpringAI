package org.example.ch03.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch03.service.FewShotPromptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class FewShotPromptController {
    private final FewShotPromptService service;

    @GetMapping("/ai/few-shot-prompt")
    public String fewShotPrompt() {
        return "few-shot-prompt";
    }

    @PostMapping("/ai/few-shot-prompt")
    @ResponseBody
    public String fewShotPrompt(@RequestParam("order") String order) {
        log.info("order: {}", order);

        return service.prompt(order);
    }
}
