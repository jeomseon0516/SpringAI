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
public class ImageGenerationController {

    private final ImageGenerationService service;

    @GetMapping("/ai/image-generation")
    public String imageGenerate() {
        return "image-generation";
    }

    @ResponseBody
    @PostMapping(
        value = "/ai/image-generate",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String imageGenerate(@RequestParam("description") String description) {
        return service.generate(description);
    }

    @ResponseBody
    @PostMapping(
        value = "/ai/image-edit",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String imageEdit(@RequestParam("description") String description,
                            @RequestParam("originalImage") MultipartFile originalImage,
                            @RequestParam("maskImage")  MultipartFile maskImage) throws IOException {
        try {
            String b64Json = service.edit(description, originalImage.getBytes(), maskImage.getBytes());
            log.info("b64Json: " + b64Json);
            return b64Json;
        } catch(Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
