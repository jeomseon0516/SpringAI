package org.example.ch06.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch06.service.ImageGenerationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@Controller
@RequiredArgsConstructor
public class VideoAnalysisController {

    @GetMapping("/ai/video-analysis")
    public String videoAnalysis() {
        return "video-analysis";
    }
}
