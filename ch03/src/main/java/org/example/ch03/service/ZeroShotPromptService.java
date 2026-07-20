package org.example.ch03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ZeroShotPromptService {
    private final ChatClient chatClient;

    private final PromptTemplate promptTemplate = PromptTemplate.builder()
        .template("""
                영화 리뷰를 [긍정적, 중립적, 부정적] 중에서 하나로 분류해주세요.
                리뷰: {review}
            """)
        .build();

    public String prompt(String review) {
        return chatClient.prompt()
            .user(promptTemplate.render(Map.of("review", review)))
            .call()
            .content();
    }
}
