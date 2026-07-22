package org.example.ch07.service;

import lombok.extern.log4j.Log4j2;
import org.example.ch07.advisor.MaxCharLengthAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AdvisorContextService {
    private final ChatClient chatClient;

    public AdvisorContextService(ChatClient.Builder builder) {
        this.chatClient = builder
            .defaultAdvisors(
                new MaxCharLengthAdvisor(Ordered.HIGHEST_PRECEDENCE),
                new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE)
            )
            .build();
    }

    public String call(String question) {
        return chatClient
            .prompt()
            .advisors(advisor -> advisor.param(MaxCharLengthAdvisor.MAX_CHAR_LENGTH, 100))
            .user(question)
            .call()
            .content();
    }
}
