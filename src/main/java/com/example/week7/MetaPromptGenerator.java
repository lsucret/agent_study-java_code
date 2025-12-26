package com.example.week7;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;

import java.util.List;
import java.util.Scanner;

public class MetaPromptGenerator {
    
    private static final String META_PROMPT = """
            Given a task description or existing prompt, produce a detailed system prompt to guide a language model in completing the task effectively.
            
            # Guidelines
            - json 내의 persona, objective, task_specifications 값을 한글로 작성해 주세요.
            - Understand the Task: Grasp the main objective, goals, requirements, constraints, and expected output.
            - Minimal Changes: If an existing prompt is provided, improve it only if it's simple. For complex prompts, enhance clarity and add missing elements without altering the original structure.
            - Reasoning Before Conclusions**: Encourage reasoning steps before any conclusions are reached. ATTENTION! If the user provides examples where the reasoning happens afterward, REVERSE the order! NEVER START EXAMPLES WITH CONCLUSIONS!
                - Reasoning Order: Call out reasoning portions of the prompt and conclusion parts (specific fields by name). For each, determine the ORDER in which this is done, and whether it needs to be reversed.
                - Conclusion, classifications, or results should ALWAYS appear last.
            - Clarity and Conciseness: Use clear, specific language. Avoid unnecessary instructions or bland statements.
            - Formatting: Use markdown features for readability. DO NOT USE ``` CODE BLOCKS UNLESS SPECIFICALLY REQUESTED.
            - Preserve User Content: If the input task or prompt includes extensive guidelines or examples, preserve them entirely, or as closely as possible. If they are vague, consider breaking down into sub-steps. Keep any details, guidelines, examples, variables, or placeholders provided by the user.
            - Constants: DO include constants in the prompt, as they are not susceptible to prompt injection. Such as guides, rubrics, and examples.
            - Output Format: Explicitly the most appropriate output format, in detail. This should include length and syntax (e.g. short sentence, paragraph, JSON, etc.)
                - For tasks outputting well-defined or structured data (classification, JSON, etc.) bias toward outputting a JSON.
                - JSON should never be wrapped in code blocks (```) unless explicitly requested.
            
            The final prompt you output should adhere to the following structure below. Do not include any additional commentary, only output the completed system prompt. SPECIFICALLY, do not include any additional messages at the start or end of the prompt. (e.g. no "---")
            
            # Output Format
            
            ```json
            {
                "persona": [The agent's persona; e.g. A seasoned project manager with 10 years of experience...],
                "objective": [The objective goes here],
                "task_specifications": [Task specifications goes here]
            }
            ```
            """.strip();

    public static void main(String[] args) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Error: OPENAI_API_KEY environment variable is required");
            System.exit(1);
        }

        ChatLanguageModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o")
                .logRequests(true)
                .build();

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Meta Prompt Generator ===");
        System.out.println("Enter your task or existing prompt (type 'exit' to quit):");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.isEmpty()) {
                System.out.println("Please enter a task or prompt.");
                continue;
            }

            try {
                System.out.println("\nGenerating meta prompt...\n");
                
                var messages = List.of(
                        new SystemMessage(META_PROMPT),
                        new UserMessage("Please craft clear Persona, Objective statement and Task specifications that can be used with an AI agent for the following:\n" + input)
                );

                var response = chatModel.generate(messages);
                
                System.out.println("Generated Prompt:");
                System.out.println("==================");
                System.out.println(response.content());
                System.out.println("==================");
            } catch (Exception e) {
                System.err.println("Error generating prompt: " + e.getMessage());
            }
        }
        scanner.close();
    }
}