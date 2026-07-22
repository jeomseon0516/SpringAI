package org.example.ch07.service;

import lombok.extern.log4j.Log4j2;
import org.example.ch07.advisor.AdvisorA;
import org.example.ch07.advisor.AdvisorB;
import org.example.ch07.advisor.AdvisorC;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Log4j2
@Service
public class AdvisorLoggingService {
    private final ChatClient chatClient;

    public AdvisorLoggingService(ChatClient.Builder builder) {
        this.chatClient = builder
            .defaultAdvisors(
                // properties 파일에 logging.level DEBUG 설정을 반드시 해야함
                new SimpleLoggerAdvisor()
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
