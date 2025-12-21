package com.example.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatModelFactory {

    private final OpenAIConfigProp openAIConfigProp;

    @Value("${ai.provider:openai}")
    private String provider;

    @Autowired
    public ChatModelFactory(OpenAIConfigProp openAIConfigProp) {
        this.openAIConfigProp = openAIConfigProp;
    }

    public ChatLanguageModel createChatModel() {
        return switch (provider.toLowerCase()) {
            case "openai" -> createOpenAiModel(openAIConfigProp);
            case "google" -> createGoogleModel(openAIConfigProp); // Mock Google Model - 실제로는 GoogleAiGeminiChatModel 사용
            default -> throw new IllegalArgumentException("Unsupported AI provider: " + provider);
        };
    }
    
    private ChatLanguageModel createOpenAiModel(AIProviderConfigProp prop) {
        return OpenAiChatModel.builder()
                .apiKey(prop.getApiKey())
                .modelName(prop.getModel())
                .temperature(0.7)
                .maxTokens(2000)
//                .logRequests(true)
//                .logResponses(true)
                .build();
    }
    
    private ChatLanguageModel createGoogleModel(AIProviderConfigProp prop) {
        return OpenAiChatModel.builder()
                .apiKey(prop.getApiKey())
                .modelName(prop.getModel())
                .temperature(0.7)
                .maxTokens(500)
                .build();
    }
}