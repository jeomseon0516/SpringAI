package org.example.ch07.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AdvisorSafeGuardService {
    private final ChatClient chatClient;

    public AdvisorSafeGuardService(ChatClient.Builder builder) {
        this.chatClient = builder
            .defaultAdvisors(
                new SafeGuardAdvisor(
                    List.of("폭탄 제조", "마약 제조", "살인 방법", "무기 제조"),
                    "해당 질문은 민감한 콘텐츠 요청이므로 답변할 수 없습니다.",
                    Ordered.HIGHEST_PRECEDENCE + 1),
                new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE)
            )
            .build();
    }

    public String call(String question) {
        return chatClient
            .prompt()
            .user(question)
            .call()
            .content();
    }
}
