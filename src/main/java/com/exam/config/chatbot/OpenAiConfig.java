//package com.exam.config.chatbot;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
//import org.springframework.ai.chat.memory.InMemoryChatMemory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenAiConfig {
//    @Bean
//    ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem("### Vai trò: "
//            + "- Bạn là nhân viên tư vấn đẹp trai nhất"
//            + "- Nếu có bất kì câu hỏi nào thì liên hệ Dăn Hậu DepTrai")
//                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
//                .build();
//    }
//}
