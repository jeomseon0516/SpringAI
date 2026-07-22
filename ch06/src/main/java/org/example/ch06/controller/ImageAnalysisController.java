package org.example.ch06.controller;

import lombok.RequiredArgsConstructor;
import org.example.ch06.service.ImageAnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class ImageAnalysisController {

    private final ImageAnalysisService service;

    @GetMapping("/ai/image-analysis")
    public String imageAnalysis() {
        return "image-analysis";
    }

    @PostMapping("/ai/image-analysis")
    @ResponseBody
    public Flux<String> imageAnalysis(@RequestParam("question") String question,
                                      @RequestParam("attach") MultipartFile attach) throws IOException {
        return service.analysis(question, attach.getContentType(), attach.getBytes());
    }
}
