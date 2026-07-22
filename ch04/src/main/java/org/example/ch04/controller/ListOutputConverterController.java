package org.example.ch04.controller;

import lombok.RequiredArgsConstructor;
import org.example.ch04.service.ListOutputConverterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ListOutputConverterController {

    private final ListOutputConverterService service;

    @GetMapping("/ai/list-output-converter")
    public String beanOutputConverter() {
        return "list-output-converter";
    }

    @ResponseBody
    @PostMapping("/ai/list-output-converter")
    public List<String> listOutputConverter(@RequestParam("city") String city) {
        return service.convertHighLevel(city);
    }
}
