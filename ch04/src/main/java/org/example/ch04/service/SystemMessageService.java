package org.example.ch04.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch04.dto.ReviewClassification;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class SystemMessageService {

    private final ChatClient chatClient;

    public ReviewClassification classifyReview(String review) {

        return chatClient
            .prompt()
            .system("""
                영화 리뷰를 [POSITIVE, NEUTRAL, NEGATIVE] 중에서 하나로 분류하고,
                유효한 JSON을 반환하세요.    
            """)
            .user("%s".formatted(review))
            .call()
            .entity(ReviewClassification.class);
    }
}
