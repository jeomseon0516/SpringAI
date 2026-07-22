package org.example.ch04.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch04.dto.HotelDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class GenericBeanOutputConverterService {

    private final ChatClient chatClient;

    public List<HotelDTO> convertLowLevel(String cities) {

        // 출력 변환기 생성
        BeanOutputConverter<List<HotelDTO>> converter
            = new BeanOutputConverter<>(new ParameterizedTypeReference<>() {});

        // 프롬프트 템플릿 생성
        PromptTemplate promptTemplate = PromptTemplate.builder()
            .template("{city}에서 유명한 호텔 목록 5개를 출력하세요. {format}")
            .build();

        // 프롬프트 생성
        Prompt prompt = promptTemplate.create(Map.of("city", cities, "format", converter.getFormat()));

        // LLM 요청 및 응답
        String answer = chatClient
            .prompt(prompt)
            .call()
            .content();

        log.info(answer);

        // Converter로 List 변환
        List<HotelDTO> answerList = converter
            .convert(Objects.requireNonNull(answer));

        log.info(answerList);

        return answerList;
    }

    public List<HotelDTO> convertHighLevel(String cities) {
        return chatClient.prompt()
            .user("%s에서 유명한 호텔 목록 5개를 출력하세요.".formatted(cities))
            .call()
            .entity(new BeanOutputConverter<>(new ParameterizedTypeReference<>() {}));
    }
}
