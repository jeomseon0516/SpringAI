package org.example.ch04.controller;

import lombok.RequiredArgsConstructor;
import org.example.ch04.dto.HotelDTO;
import org.example.ch04.service.GenericBeanOutputConverterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GenericBeanOutputConverterController {

    private final GenericBeanOutputConverterService service;

    @GetMapping("/ai/generic-bean-output-converter")
    public String beanOutputConverter() {
        return "generic-bean-output-converter";
    }

    @ResponseBody
    @PostMapping("/ai/generic-bean-output-converter")
    public List<HotelDTO> beanOutputConverter(@RequestParam("cities") String cities) {
        return service.convertHighLevel(cities);
    }
}
