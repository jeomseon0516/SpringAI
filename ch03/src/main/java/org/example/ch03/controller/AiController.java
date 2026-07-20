package org.example.ch03.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch03.service.AiService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AiController {
    private final AiService aiService;

    @GetMapping("/ai/prompt-template")
    public String promptTemplate() {
        return "prompt-template";
    }

    @PostMapping(
        value = "/ai/prompt-template",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    public Flux<String> promptTemplate(@RequestParam("statement")   String statement,
                                       @RequestParam("language")    String language ) {
        log.info("promptTemplate: {}", statement);
        log.info("language: {}", language);

        return aiService.promptTemplate3(statement, language);
    }
}
