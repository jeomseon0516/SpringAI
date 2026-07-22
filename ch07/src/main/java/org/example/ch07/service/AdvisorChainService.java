package org.example.ch07.service;

import lombok.extern.log4j.Log4j2;
import org.example.ch07.advisor.AdvisorA;
import org.example.ch07.advisor.AdvisorB;
import org.example.ch07.advisor.AdvisorC;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Log4j2
@Service
public class AdvisorChainService {
    private final ChatClient chatClient;

    public AdvisorChainService(ChatClient.Builder builder) {
        this.chatClient = builder
            .defaultAdvisors(
                new AdvisorA(),
                new AdvisorB(),
                new AdvisorC()
            )
            .build();
    }

    public String call(String question) {
        return chatClient
            .prompt()
            .advisors(new AdvisorC())
            .user(question)
            .call()
            .content();
    }

    public Flux<String> stream(String question) {
        return chatClient
            .prompt()
            .advisors(new AdvisorC())
            .user(question)
            .stream()
            .content();
    }
}
