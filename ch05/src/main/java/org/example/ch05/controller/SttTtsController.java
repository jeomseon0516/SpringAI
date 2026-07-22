package org.example.ch05.controller;

import lombok.RequiredArgsConstructor;
import org.example.ch05.service.SttTtsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class SttTtsController {
    private final SttTtsService service;

    @GetMapping("/ai/stt-tts")
    public String sttTts() {
        return "stt-tts";
    }

    @ResponseBody
    @PostMapping("/ai/stt")
    public String stt(@RequestParam("speech") MultipartFile speech) throws IOException {

        String fname = speech.getOriginalFilename();
        byte[] bytes = speech.getBytes();

        return service.stt(fname, bytes);
    }

    @ResponseBody
    @PostMapping("/ai/tts")
    public byte[] tts(@RequestParam("text") String text) {
        return service.tts(text);
    }
}
