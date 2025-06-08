package com.exam.chatbot.config;

import org.springframework.ai.chat.memory.ChatMemory;

import org.springframework.ai.chat.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ExpiringChatMemory implements ChatMemory {

    private static final long EXPIRATION_MILLIS = 48 * 60 * 60 * 1000; // 48 giờ

    private static class TimedMessage {
        private final Message message;
        private final long timestamp;

        public TimedMessage(Message message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

        public Message getMessage() {
            return message;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    private final Map<String, List<TimedMessage>> conversationHistory = new ConcurrentHashMap<>();

    public ExpiringChatMemory() {
        startCleanupTask();
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        conversationHistory.putIfAbsent(conversationId, new ArrayList<>());
        List<TimedMessage> timedMessages = messages.stream()
                .map(TimedMessage::new)
                .collect(Collectors.toList());
        conversationHistory.get(conversationId).addAll(timedMessages);

        cleanupOldMessages(conversationId);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<TimedMessage> all = conversationHistory.get(conversationId);
        if (all == null) {
            return List.of();
        }

        long now = System.currentTimeMillis();

        List<TimedMessage> validMessages = all.stream()
                .filter(m -> now - m.getTimestamp() <= EXPIRATION_MILLIS)
                .collect(Collectors.toList());

        int size = validMessages.size();
        int start = Math.max(0, size - lastN);

        return validMessages.subList(start, size).stream()
                .map(TimedMessage::getMessage)
                .collect(Collectors.toList());
    }

    @Override
    public void clear(String conversationId) {
        conversationHistory.remove(conversationId);
    }

    private void cleanupOldMessages(String conversationId) {
        List<TimedMessage> messages = conversationHistory.get(conversationId);
        if (messages == null) return;

        long now = System.currentTimeMillis();
        messages.removeIf(m -> now - m.getTimestamp() > EXPIRATION_MILLIS);

        if (messages.isEmpty()) {
            conversationHistory.remove(conversationId);
        }
    }

    private void startCleanupTask() {
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60 * 60 * 1000); // dọn dẹp mỗi 1 giờ
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                long now = System.currentTimeMillis();
                for (String conversationId : conversationHistory.keySet()) {
                    List<TimedMessage> messages = conversationHistory.get(conversationId);
                    if (messages != null) {
                        messages.removeIf(m -> now - m.getTimestamp() > EXPIRATION_MILLIS);
                        if (messages.isEmpty()) {
                            conversationHistory.remove(conversationId);
                        }
                    }
                }
            }
        });

        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }
}