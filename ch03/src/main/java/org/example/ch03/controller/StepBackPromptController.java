package org.example.ch03.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch03.service.StepBackPromptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j2
@RequiredArgsConstructor
public class StepBackPromptController {

    private final StepBackPromptService service;

    @GetMapping("/ai/step-back-prompt")
    public String stepBackPrompt() {
        return "step-back-prompt";
    }

    @PostMapping("/ai/step-back-prompt")
    @ResponseBody
    public String stepBackPrompt(@RequestParam("question") String question) {
        log.info("question:{}", question);

        return service.prompt(question);
    }
}
