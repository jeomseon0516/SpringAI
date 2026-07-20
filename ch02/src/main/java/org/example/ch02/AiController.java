package org.example.ch02;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AiController {

    private final ChatModel chatModel;

    @PostMapping(
        value ="/ai/chat",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, // 클라이언트가 보내는 데이터타입, 기본 폼 전송, 일반적으로 생략
        produces = MediaType.APPLICATION_NDJSON_VALUE           // NDJSON(Newline Delimited JSON), JSON 객체를 행단위로 전송하는 형식                   // 서버가 응답하는 데이터타입, 일반 텍스트, 일반적으로 생략
    )
    public Flux<String> chat(@RequestParam("question") String question) {

        // 시스템 메시지 작성
        SystemMessage systemMessage = SystemMessage.builder()
            .text("사용자 질문에 한국어로 답변해야 됩니다.")
            .build();

        // 사용자 메시지 작성
        UserMessage userMessage = UserMessage.builder()
            .text(question)
            .build();

        // 대화 옵션 설정
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
            .model("gpt-4o-mini")
            .temperature(0.1)   // LLM 응답 창의성ㅇ을 조절하는 하이퍼파라미터, 낮은값은 일관성있고 예측 가능한 답변 생성, 0 ~ 1 사이 실수값
            .maxTokens(200)     // 최대 문자 갯수
            .build();

        // 프롬프트 생성
        Prompt prompt = Prompt.builder()
            .messages(systemMessage, userMessage)
            .chatOptions(chatOptions)
            .build();

        // LLM 요청 및 응답
        return chatModel
            .stream(prompt)
            .map(chatResponse
                -> Objects.requireNonNull(
                    Objects.requireNonNull(chatResponse
                        .getResult())
                .getOutput()
                .getText()));
    }
}
