package com.example.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

public class OpenAIService {
    private final OpenAiService openAiService;
    
    public OpenAIService(String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }
    
    public String createChatCompletion(String model, List<ChatMessage> messages) {
        try {
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(500)
                    .temperature(0.7)
                    .stream(true)
                    .build();
            
            StringBuilder result = new StringBuilder();
            
            openAiService.streamChatCompletion(request)
                    .doOnNext(chunk -> {
                        if (chunk.getChoices() != null && !chunk.getChoices().isEmpty()) {
                            var delta = chunk.getChoices().get(0).getMessage();
                            if (delta != null && delta.getContent() != null) {
                                result.append(delta.getContent());
                                System.out.print(delta.getContent()); // 실시간 출력
                            }
                        }
                    })
                    .doOnComplete(() -> System.out.println("\n[Stream Complete]"))
                    .blockingSubscribe();
                    
            return result.toString();
        } catch (Exception e) {
            System.err.println("OpenAI API Error: " + e.getMessage());
            return "Travel planning service is temporarily unavailable. Please try again later.";
        }
    }
}