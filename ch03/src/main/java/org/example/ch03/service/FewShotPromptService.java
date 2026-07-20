package org.example.ch03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class FewShotPromptService {
    private final ChatClient chatClient;

    public String prompt(String order) {

        String strPrompt = """
            주문을 JSON 형식으로 변환하시오.
            추가 설명은 하지마세요.
           
            예시1:
            작은 피자 하나, 치즈랑 토마토 소스, 페퍼로니로 주세요.
            
            JSON 응답:
            {
                "size": "small",
                "type": "normal",
                "ingredients": ["cheese", "tomato sauce", "pepperoni"]
            }
            
            고객주문 : %s""".formatted(order);

        Prompt prompt = Prompt.builder()
            .content(strPrompt)
            .build();

        return chatClient
            .prompt(prompt)
            .call()
            .content();
    }
}
