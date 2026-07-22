package org.example.ch05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatVoiceOneModelController {

    @GetMapping("/ai/chat-voice-one-model")
    public String chatVoiceOneModel() {
        return "chat-voice-one-model";
    }
}
