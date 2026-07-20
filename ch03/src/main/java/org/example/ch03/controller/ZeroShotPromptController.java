package org.example.ch03.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch03.service.AiService;
import org.example.ch03.service.ZeroShotPromptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ZeroShotPromptController {
    private final ZeroShotPromptService service;

    @GetMapping("/ai/zero-shot-prompt")
    public String zeroShotPrompt() {
        return "zero-shot-prompt";
    }

    @PostMapping("/ai/zero-shot-prompt")
    @ResponseBody
    public String zeroShotPrompt(@RequestParam("review") String review) {
        log.info("review: {}", review);

        return service.prompt(review);
    }
}
