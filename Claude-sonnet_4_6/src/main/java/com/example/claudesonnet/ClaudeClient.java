package com.example.claudesonnet;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Minimal client for calling the Anthropic Messages API.
 * Used to send prompts (from the Prompt/ folder) to Claude Sonnet 4.6
 * and store the raw responses under Result/.
 *
 * Set your API key as an environment variable before running:
 *   export ANTHROPIC_API_KEY=sk-ant-xxxx   (Linux/macOS)
 *   set ANTHROPIC_API_KEY=sk-ant-xxxx      (Windows)
 */
public class ClaudeClient {

    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final String MODEL = "claude-sonnet-4-6";

    public String sendPrompt(String promptText) throws Exception {
        String apiKey = System.getenv("ANTHROPIC_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Missing ANTHROPIC_API_KEY environment variable.");
        }

        String escapedPrompt = promptText
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");

        String jsonBody = """
                {
                  "model": "%s",
                  "max_tokens": 1024,
                  "messages": [
                    {"role": "user", "content": "%s"}
                  ]
                }
                """.formatted(MODEL, escapedPrompt);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) throws Exception {
        Path promptFile = Path.of("Prompt", "generate_test_prompt.txt");
        Path resultFile = Path.of("Result", "response.json");

        String prompt = Files.readString(promptFile);

        ClaudeClient client = new ClaudeClient();
        String result = client.sendPrompt(prompt);

        Files.createDirectories(resultFile.getParent());
        Files.writeString(resultFile, result);

        System.out.println("Response saved to " + resultFile);
    }
}
