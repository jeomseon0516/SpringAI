package org.example.ch04.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch04.dto.HotelDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class BeanOutputConverterService {

    private final ChatClient chatClient;

    public HotelDTO convertLowLevel(String city) {

        // 출력 변환기 생성
        BeanOutputConverter<HotelDTO> converter = new BeanOutputConverter<>(HotelDTO.class);

        // 프롬프트 템플릿 생성
        PromptTemplate promptTemplate = PromptTemplate.builder()
            .template("{city}에서 유명한 호텔 목록 5개를 출력하세요. {format}")
            .build();

        // 프롬프트 생성
        Prompt prompt = promptTemplate.create(Map.of("city", city, "format", converter.getFormat()));

        // LLM 요청 및 응답
        String json = chatClient
            .prompt(prompt)
            .call()
            .content();

        log.info(json);

        // Converter로 List 변환
        HotelDTO hotel = converter
            .convert(Objects.requireNonNull(json));

        log.info(hotel);

        return hotel;
    }

    public HotelDTO convertHighLevel(String city) {
        return chatClient.prompt()
            .user("%s에서 유명한 호텔 목록 5개를 출력하세요.".formatted(city))
            .call()
            .entity(HotelDTO.class);
    }
}
