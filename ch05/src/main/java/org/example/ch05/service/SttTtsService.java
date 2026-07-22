package org.example.ch05.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SttTtsService {

    private final ChatClient chatClient;
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;

    public String stt(String fname, byte[] bytes) {

        // 파일명 정의
        Resource resource = new ByteArrayResource(bytes) {
            @Override
            public String getFilename() { return fname;}
        };

        // 모델 옵션 설정
        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
            .model("whisper-1")
            .language("ko")
            .build();

        // 프롬프트 생성
        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(resource, options);

        // 모델 요청 및 응답
        AudioTranscriptionResponse response = openAiAudioTranscriptionModel.call(prompt);

        return response.getResult().getOutput();
    }

    public byte[] tts(String text) {

        // 모델 옵션 설정
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
            .model("gpt-4o-mini-tts")
            .voice(OpenAiAudioSpeechOptions.Voice.ALLOY)
            .responseFormat(OpenAiAudioSpeechOptions.AudioResponseFormat.MP3)
            .speed(1.0)
            .build();

        // 프롬프트 생성
        TextToSpeechPrompt prompt = new TextToSpeechPrompt(text, options);

        // 모델 요청 및 응답
        TextToSpeechResponse response = openAiAudioSpeechModel.call(prompt);

        return response.getResult().getOutput();
    }
}
