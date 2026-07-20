package org.example.ch03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class RoleAssignmentService {
    private final ChatClient chatClient;

    private final PromptTemplate systemPromptTemplate = SystemPromptTemplate.builder()
        .template("""
            당신이 여행 가이드 역할을 해 주었으면 좋겠습니다.
            아래 요청사항에서 위치를 알려주면, 근처에 있는 3곳을 제안해 주고,
            이유를 달아주세요. 경우에 따라서 방문하고 싶은 장소 유형을 제공할 수도 있습니다.
        """)
        .build();

    private final PromptTemplate userPromptTemplate = PromptTemplate.builder()
        .template("요청사항: {requirements}")
        .build();


    public String prompt(String requirements) {
        return chatClient.prompt()
            .system(systemPromptTemplate.render())
            .user(userPromptTemplate.render(Map.of("requirements", requirements)))
            .call()
            .content();
    }
}
