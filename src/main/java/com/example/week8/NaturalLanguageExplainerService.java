package com.example.week8;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Natural Language Explainer Service
 * Python의 Chapter_08_xai.ipynb Natural Language Explanations 구현
 */
public class NaturalLanguageExplainerService {

    private final ExplainerAI explainer;

    interface ExplainerAI {
        @SystemMessage("You are an explainability assistant.")
        @UserMessage("Explain why '{{query}}' is important in the context of {{context}}.")
        String explain(@V("query") String query, @V("context") String context);
    }

    public NaturalLanguageExplainerService(String apiKey) {
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini")
                .build();

        this.explainer = AiServices.create(ExplainerAI.class, model);
    }

    public String explain(String query) {
        return explainer.explain(query, "travel");
    }

    public static void main(String[] args) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("OPENAI_API_KEY 환경변수를 설정해주세요.");
            return;
        }
        
        NaturalLanguageExplainerService service = new NaturalLanguageExplainerService(apiKey);
        
        String query = "What are the best family-friendly travel destinations in Europe?";
        System.out.println("Query: " + query);
        System.out.println("\nExplanation:");
        System.out.println(service.explain(query));
    }
}
