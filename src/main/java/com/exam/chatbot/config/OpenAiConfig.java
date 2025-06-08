package com.exam.chatbot.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {
    @Bean
    ChatClient chatClient(ChatClient.Builder builder){
        return builder.defaultSystem("### Vai trò: " +
                "Bạn là 1 AI hoặc 1 chatbot hỗ trợ và trả lời các câu hỏi từ người dùng. Nếu câu hỏi của người dùng nằm ngoài vấn đề data đang có, thì bạn có thể trả lời theo những kiến tức và ý của bạn.").defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory())).build();
    }
}
