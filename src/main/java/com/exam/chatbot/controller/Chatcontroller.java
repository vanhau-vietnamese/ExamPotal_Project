package com.exam.chatbot.controller;

import com.exam.chatbot.dto.ChatRequestDto;
import com.exam.chatbot.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class Chatcontroller {
    private final AgentService agentService;

    @GetMapping("/ask")
    public ResponseEntity<String> generation(@RequestParam("message") String message, @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        return ResponseEntity.ok(agentService.chatMessage(message, files));
    }

    @PostMapping("")
    public ResponseEntity<String> doChat(@RequestBody ChatRequestDto request){
        return ResponseEntity.ok(agentService.doChat(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Message>> getHistory(){
        return ResponseEntity.ok(agentService.getHistory());
    }

    @DeleteMapping("/history/clear")
    public ResponseEntity<String> clearHistory() {
        return ResponseEntity.ok(agentService.clearHistory());
    }
}
