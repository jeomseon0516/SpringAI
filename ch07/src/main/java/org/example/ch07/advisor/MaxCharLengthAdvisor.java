package org.example.ch07.advisor;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.Ordered;
import reactor.core.publisher.Flux;

@Log4j2
public class MaxCharLengthAdvisor implements CallAdvisor {

    public static final String MAX_CHAR_LENGTH = "maxCharLength";
    private int maxCharLength = 300;
    private int order;

    public MaxCharLengthAdvisor(int order) {
        this.order = order;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        log.info("[전처리]");

        ChatClientResponse response = callAdvisorChain
            .nextCall(augmentPrompt(chatClientRequest));

        log.info("[후처리]");

        return response;
    }

    public ChatClientRequest augmentPrompt(ChatClientRequest request) {

        String userText = this.maxCharLength + "자 이내로 답변하시오.";

        Integer maxCharLength = (Integer)request.context().get(MAX_CHAR_LENGTH);

        if (maxCharLength != null) {
            userText = maxCharLength + "자 이내로 답변하시오.";
        }

        String finalUserText = userText;

        // 사용자 메시지를 강화한 Prompt 생성
        Prompt originalPrompt = request.prompt();
        Prompt augmentedPrompt = originalPrompt.augmentUserMessage(userMessage -> UserMessage.builder()
            .text(userMessage.getText() + " " + finalUserText)
            .build());

        // 수정된 ChatClientRequest 생성
        return request
            .mutate()
            .prompt(augmentedPrompt)
            .build();
    }
}
