package org.example.ch03.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Log4j2
public class StepBackPromptService {
    private final ChatClient chatClient;

    private final PromptTemplate systemPromptTemplate = SystemPromptTemplate.builder()
        .template("""
            사용자 질문을 처리하기 Step-Back 프롬프트 기법을 사용하려고 합니다.
            사용자 질문을 단계별 질문들로 재구성해 주세요.
            맨 마지막 질문은 사용자 질문과 일치해야 합니다.
            단계별 질문을 항목으로 하는 JSON 배열을 출력해 주세요.
            예시: ["...", "...", "...", "..."]
        """)
        .build();

    private final PromptTemplate userPromptTemplate = PromptTemplate.builder()
        .template("""
            {question}
            문맥: {context}
        """)
        .build();

    public String prompt(String question) {
        String questions = chatClient.prompt()
            .system(systemPromptTemplate.render())
            .user(userPromptTemplate.render(Map.of("question", question)))
            .call()
            .content();

        String json = questions.substring(questions.indexOf("["), questions.indexOf("]") + 1);
        log.info(json);

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> listQuestions = objectMapper.readValue(
            json,
            new TypeReference<List<String>>() {}
        );

/*        String[] answers = listQuestions.stream()
            .*/
        return null;
    }

    public String getStepAnswer(String question, String... prevStepAnswers) {
        String context = String.join("", prevStepAnswers);

        return chatClient.prompt()
            .user(userPromptTemplate.render(question, context))
            .call()
            .content();
    }
}
