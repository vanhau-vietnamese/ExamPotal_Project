package com.exam.chatbot.controller;

import com.exam.chatbot.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agent")
public class AgentController {
    private final AgentService agentService;
    @PostMapping("/store_db")
    public String storeDb(@RequestParam("file") MultipartFile file) throws IOException {
        return agentService.processAndStore(file);
    }

    @GetMapping("/image")
    public String imageToCode() throws IOException {
        return agentService.imageToCode();
    }
}
