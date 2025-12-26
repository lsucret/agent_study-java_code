package com.example.week7;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShortTermMemoryAgent {
    
    // State class to represent conversation state
    public static class TravelState {
        private final List<ChatMessage> messages;
        
        public TravelState() {
            this.messages = new ArrayList<>();
        }
        
        public TravelState(List<ChatMessage> messages) {
            this.messages = new ArrayList<>(messages);
        }
        
        public List<ChatMessage> getMessages() {
            return new ArrayList<>(messages);
        }
        
        public void addMessage(ChatMessage message) {
            messages.add(message);
        }
    }
    
    private static final String SYSTEM_PROMPT = """
            You are a helpful travel agent assistant. Use the conversation history to 
            remember the user's preferences and trip details. Be specific and reference 
            their previously mentioned preferences when making recommendations.
            """;
    
    private final ChatLanguageModel chatModel;
    private final Map<String, TravelState> threadStates;
    
    public ShortTermMemoryAgent(String apiKey) {
        this.chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o")
                .logRequests(true)
                .build();
        this.threadStates = new ConcurrentHashMap<>();
    }
    
    public String chatWithTravelAgent(String message, String threadId) {
        // Get or create state for this thread
        TravelState state = threadStates.computeIfAbsent(threadId, k -> {
            TravelState newState = new TravelState();
            newState.addMessage(new SystemMessage(SYSTEM_PROMPT));
            return newState;
        });
        
        // Add user message to state
        state.addMessage(new UserMessage(message));
        
        // Generate response using current state
        TravelState updatedState = generateResponse(state);
        
        // Update stored state
        threadStates.put(threadId, updatedState);
        
        // Return the last AI message
        List<ChatMessage> messages = updatedState.getMessages();
        return ((AiMessage) messages.get(messages.size() - 1)).text();
    }
    
    private TravelState generateResponse(TravelState state) {
        // Generate response using all messages in state
        var response = chatModel.generate(state.getMessages());
        
        // Create new state with response added
        TravelState newState = new TravelState(state.getMessages());
        newState.addMessage(new AiMessage(response.content().toString())); //
        
        return newState;
    }
    
    public void clearThread(String threadId) {
        threadStates.remove(threadId);
    }
    
    public static void main(String[] args) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Error: OPENAI_API_KEY environment variable is required");
            System.exit(1);
        }
        
        ShortTermMemoryAgent agent = new ShortTermMemoryAgent(apiKey);
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Short-Term Memory Travel Agent ===\n");
        System.out.println("Commands: 'new' (new thread), 'exit' (quit)");
        System.out.println("Enter your travel questions:\n");
        
        String currentThreadId = "user_123";
        
        while (true) {
            System.out.print("[Thread: " + currentThreadId + "] > ");
            String input = scanner.nextLine().trim();
            
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Goodbye!");
                break;
            }
            
            if ("new".equalsIgnoreCase(input)) {
                currentThreadId = "user_" + System.currentTimeMillis();
                System.out.println("Started new conversation thread: " + currentThreadId + "\n");
                continue;
            }
            
            if (input.isEmpty()) {
                System.out.println("Please enter a message.");
                continue;
            }
            
            try {
                String response = agent.chatWithTravelAgent(input, currentThreadId);
                System.out.println("Agent: " + response + "\n");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
        
        // Demo the functionality programmatically
        System.out.println("\n=== Demo ===");
        demonstrateMemory(agent);
    }
    
    private static void demonstrateMemory(ShortTermMemoryAgent agent) {
        String threadId = "demo_thread";
        
        System.out.println("User: I want to plan a trip to Japan next month.");
        String response1 = agent.chatWithTravelAgent("I want to plan a trip to Japan next month.", threadId);
        System.out.println("Agent: " + response1 + "\n");
        
        System.out.println("User: I'm interested in traditional culture and my budget is $3000.");
        String response2 = agent.chatWithTravelAgent("I'm interested in traditional culture and my budget is $3000.", threadId);
        System.out.println("Agent: " + response2 + "\n");
        
        System.out.println("User: What was my destination again?");
        String response3 = agent.chatWithTravelAgent("What was my destination again?", threadId);
        System.out.println("Agent: " + response3 + "\n");
        
        // New conversation thread (should not know about Japan)
        String newThread = "demo_thread_2";
        System.out.println("=== New Conversation ===");
        System.out.println("User: What kind of budget would I need for a beach vacation?");
        String response4 = agent.chatWithTravelAgent("What kind of budget would I need for a beach vacation?", newThread);
        System.out.println("Agent: " + response4);
    }
}

/*
# Short term memory

## 메모리 작동 원리
1. LLM은 상태를 기억하지 않습니다
- OpenAI GPT 모델은 stateless입니다
- 각 API 호출은 독립적이며 이전 대화를 기억하지 못합니다

2. 전체 대화 히스토리를 매번 전송
```java
private TravelState generateResponse(TravelState state) {
    // 🔑 핵심: 전체 메시지 히스토리를 LLM에 전송
    var response = chatModel.generate(state.getMessages()); // 모든 메시지 전송

    TravelState newState = new TravelState(state.getMessages());
    newState.addMessage(new AiMessage(response.content()));
    return newState;
}
```

3. 메시지 구조 예시

```text
첫 번째 호출:
[SystemMessage: "You are a travel agent..."]
[UserMessage: "I want to go to Japan"]
→ LLM 응답: "Japan is wonderful..."

두 번째 호출:
[SystemMessage: "You are a travel agent..."]
[UserMessage: "I want to go to Japan"]           ← 이전 대화
[AiMessage: "Japan is wonderful..."]             ← 이전 응답
[UserMessage: "My budget is $3000"]              ← 새 메시지
→ LLM 응답: "With $3000 for Japan..."

세 번째 호출:
[SystemMessage: "You are a travel agent..."]
[UserMessage: "I want to go to Japan"]           ← 모든 이전 대화
[AiMessage: "Japan is wonderful..."]
[UserMessage: "My budget is $3000"]
[AiMessage: "With $3000 for Japan..."]
[UserMessage: "What was my destination?"]        ← 새 메시지
→ LLM 응답: "You mentioned Japan..."             ← 히스토리 참조 가능
```

4. 스레드별 분리
```java
private final Map<String, TravelState> threadStates; // 스레드별 상태 저장

// 스레드 A: Japan 대화 히스토리
// 스레드 B: 완전히 새로운 대화 히스토리
```

### 핵심 포인트
- TravelState 자체는 LLM에 전송되지 않습니다
- TravelState.getMessages()의 모든 메시지가 LLM에 전송됩니다
- LLM은 매번 전체 대화 히스토리를 받아서 컨텍스트를 이해합니다
- 애플리케이션이 메모리 역할을 담당합니다 (LLM이 아님)

이것이 ChatGPT 웹사이트에서도 긴 대화가 가능한 이유입니다.

## API직접 호출시 문제점
1. 토큰 사용량 급증
2. 네트워크 트래픽 증가
3. 비용 문제

* 위 내용은 api 직접 호출로 발생하는 문제로, chatGpt 애플리케이션을 활용하면 애플리케이션 레이어에서 상태를 관리한다.
* 다만 컨텍스트 윈도우 초과하면 이전 내용을 잊어버린 채 답변하므로 주의.

## 해결 방법
1. 메시지 제한(Sliding Window)
2. 요약 기법(Summarization)
3. 중요 정보만 추출 (Key Information Extraction)
4. 토큰 카운팅 및 관리
*
* */