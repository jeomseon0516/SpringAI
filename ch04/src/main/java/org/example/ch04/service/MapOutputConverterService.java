package org.example.ch04.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class MapOutputConverterService {

    private final ChatClient chatClient;

    public Map<String, Object> convertLowLevel(String hotel) {

        // 출력 변환기 생성
        MapOutputConverter converter = new MapOutputConverter();

        // 프롬프트 템플릿 생성
        PromptTemplate promptTemplate = PromptTemplate.builder()
            .template("{city}에서 유명한 호텔 목록 5개를 출력하세요. {format}")
            .build();

        // 프롬프트 생성
        Prompt prompt = promptTemplate.create(Map.of("city", hotel, "format", converter.getFormat()));

        // LLM 요청 및 응답
        String answer = chatClient
            .prompt(prompt)
            .call()
            .content();

        log.info(answer);

        // Converter로 List 변환
        Map<String, Object> answerMap = converter
            .convert(Objects.requireNonNull(answer));

        log.info(answerMap);

        return answerMap;
    }

    public Map<String, Object> convertHighLevel(String hotel) {
        return chatClient.prompt()
            .user("%s에서 유명한 호텔 목록 5개를 출력하세요.".formatted(hotel))
            .call()
            .entity(new MapOutputConverter());
    }
}
