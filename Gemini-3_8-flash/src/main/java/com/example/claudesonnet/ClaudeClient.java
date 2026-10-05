package com.example.claudesonnet;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Client for calling Claude Sonnet 5.5 through KKU IntelSphere API.
 */
public class ClaudeClient {

    private static final String API_URL =
            "https://gen.ai.kku.ac.th/api/v1/messages";

    private static final String MODEL =
            "gemini-3.8-flash";

    public String sendPrompt(String promptText) throws Exception {

        String apiKey = System.getenv("KKU_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Missing KKU_API_KEY environment variable."
            );
        }

        StringBuilder escapedPromptBuilder = new StringBuilder();

for (int i = 0; i < promptText.length(); i++) {
    char c = promptText.charAt(i);

    switch (c) {
        case '\\':
            escapedPromptBuilder.append("\\\\");
            break;
        case '"':
            escapedPromptBuilder.append("\\\"");
            break;
        case '\n':
            escapedPromptBuilder.append("\\n");
            break;
        case '\r':
            escapedPromptBuilder.append("\\r");
            break;
        case '\t':
            escapedPromptBuilder.append("\\t");
            break;
        case '\b':
            escapedPromptBuilder.append("\\b");
            break;
        case '\f':
            escapedPromptBuilder.append("\\f");
            break;
        default:
            if (c < 0x20) {
                escapedPromptBuilder.append(String.format("\\u%04x", (int) c));
            } else {
                escapedPromptBuilder.append(c);
            }
            break;
    }
}

String escapedPrompt = escapedPromptBuilder.toString();

        String jsonBody = """
                {
                  "model": "%s",
                  "max_tokens": 4096,
                  "messages": [
                    {
                      "role": "user",
                      "content": "%s"
                    }
                  ]
                }
                """.formatted(MODEL, escapedPrompt);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "KKU API request failed. HTTP "
                            + response.statusCode()
                            + ": "
                            + response.body()
            );
        }

        return response.body();
    }

    /**
     * Extract only Claude's text from the SSE response.
     *
     * The response from KKU IntelSphere is returned as SSE.
     * Each text_delta contains a JSON-escaped text string.
     */
    private static String extractClaudeText(String sseResponse) {

        StringBuilder result = new StringBuilder();

        String[] lines = sseResponse.split("\\R");

        for (String line : lines) {

            if (!line.startsWith("data: ")) {
                continue;
            }

            String data = line.substring(6);

            if (!data.contains("\"text_delta\"")) {
                continue;
            }

            int textIndex =
                    data.indexOf("\"text\":\"");

            if (textIndex == -1) {
                continue;
            }

            int start =
                    textIndex + 8;

            int end =
                    findJsonStringEnd(
                            data,
                            start
                    );

            if (end == -1) {
                continue;
            }

            String text =
                    data.substring(
                            start,
                            end
                    );

            StringBuilder decoded =
                    new StringBuilder();

            boolean escaped = false;

            for (int i = 0;
                 i < text.length();
                 i++) {

                char c = text.charAt(i);

                if (!escaped) {

                    if (c == '\\') {
                        escaped = true;
                    } else {
                        decoded.append(c);
                    }

                    continue;
                }

                switch (c) {

                    case 'n':
                        decoded.append('\n');
                        break;

                    case 'r':
                        decoded.append('\r');
                        break;

                    case 't':
                        decoded.append('\t');
                        break;

                    case 'b':
                        decoded.append('\b');
                        break;

                    case 'f':
                        decoded.append('\f');
                        break;

                    case '"':
                        decoded.append('"');
                        break;

                    case '\\':
                        decoded.append('\\');
                        break;

                    case '/':
                        decoded.append('/');
                        break;

                    default:
                        decoded.append('\\');
                        decoded.append(c);
                        break;
                }

                escaped = false;
            }

            if (escaped) {
                decoded.append('\\');
            }

            result.append(
                    decoded.toString()
            );
        }

        return result.toString();
    }

    /**
     * Find the closing quote of a JSON string.
     */
    private static int findJsonStringEnd(
            String text,
            int start
    ) {

        boolean escaped = false;

        for (int i = start;
             i < text.length();
             i++) {

            char c = text.charAt(i);

            if (escaped) {
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (c == '"') {
                return i;
            }
        }

        return -1;
    }

    /**
     * Extract Java source code from Claude's response.
     *
     * Supported response formats:
     *
     * 1. === TEST CODE ===
     *    ```java
     *    ...
     *    ```
     *
     * 2. ```java
     *    ...
     *    ```
     *
     * 3. Plain Java source code containing a package/class declaration.
     */
    static String extractTestCode(String response) {

        String section =
                response.trim();

        String marker =
                "=== TEST CODE ===";

        int markerStart =
                section.indexOf(marker);

        if (markerStart >= 0) {

            section =
                    section.substring(
                            markerStart + marker.length()
                    );

            int targetMarker =
                    section.indexOf(
                            "=== TARGET TEST ==="
                    );

            if (targetMarker >= 0) {

                section =
                        section.substring(
                                0,
                                targetMarker
                        );
            }

            section =
                    section.trim();
        }


        // Prefer the fenced block that is a full test file (has a package line)
int scan = 0;
while (true) {
    int f = section.indexOf("```java", scan);
    if (f < 0) break;
    int s = f + "```java".length();
    int e = section.indexOf("```", s);
    String block = (e >= 0) ? section.substring(s, e) : section.substring(s);
    if (block.contains("package ")) {
        return block.trim();
    }
    if (e < 0) break;
    scan = e + 3;
}

        /*
         * Claude normally returns:
         *
         * ```java
         * public class ...
         * ```
         */
        int javaFence =
                section.indexOf(
                        "```java"
                );

        if (javaFence >= 0) {

            int codeStart =
                    javaFence + "```java".length();

            int codeEnd =
                    section.indexOf(
                            "```",
                            codeStart
                    );

            if (codeEnd >= 0) {

                section =
                        section.substring(
                                codeStart,
                                codeEnd
                        );

            } else {

                section =
                        section.substring(
                                codeStart
                        );
            }

            return section.trim();
        }

        /*
         * Support generic Markdown code fence:
         *
         * ```
         * ...
         * ```
         */
        int genericFence =
                section.indexOf(
                        "```"
                );

        if (genericFence >= 0) {

            int codeStart =
                    section.indexOf(
                            '\n',
                            genericFence
                    );

            if (codeStart >= 0) {

                codeStart++;

                int codeEnd =
                        section.indexOf(
                                "```",
                                codeStart
                        );

                if (codeEnd >= 0) {

                    section =
                            section.substring(
                                    codeStart,
                                    codeEnd
                            );

                    return section.trim();
                }
            }
        }

        /*
         * If there is no Markdown fence, look for the
         * beginning of Java source code.
         */
        int packageIndex =
                section.indexOf(
                        "package "
                );

        int importIndex =
                section.indexOf(
                        "import "
                );

        int classIndex =
                section.indexOf(
                        "public class "
                );

        int start =
                -1;

        if (packageIndex >= 0) {

            start =
                    packageIndex;

        } else if (importIndex >= 0) {

            start =
                    importIndex;

        } else if (classIndex >= 0) {

            start =
                    classIndex;
        }

        if (start >= 0) {

            section =
                    section.substring(
                            start
                    );

            return section.trim();
        }

        throw new IllegalArgumentException(
                "Could not extract Java test code from Claude response."
        );
    }

    /**
     * Find the public class name from Java source code.
     */
    static String findClassName(String javaCode) {

        Pattern pattern = Pattern.compile(
                "public\\s+(?:(?:final|abstract)\\s+)*class\\s+([A-Za-z_$][A-Za-z0-9_$]*)"
        );

        Matcher matcher =
                pattern.matcher(
                        javaCode
                );

        if (!matcher.find()) {

            throw new IllegalArgumentException(
                    "Could not find public class name "
                            + "in generated test."
            );
        }

        return matcher.group(1);
    }

    /**
     * Append one generation result to raw_results.csv.
     *
     * This method does not add duplicate rows for the same
     * Project + Bug + Generated Test File combination.
     */
    private static void appendRawResult(
            String project,
            String bug,
            Path promptFile,
            Path rawResultFile,
            Path cleanResultFile,
            Path testCodeFile
    ) throws Exception {

        Path csvFile =
                Path.of(
                        "Result",
                        "raw_results.csv"
                );

        String generatedTestPath =
                testCodeFile.toString();

        String csvRow =
                String.join(
                        ",",
                        project,
                        bug,
                        MODEL,
                        promptFile.toString(),
                        rawResultFile.toString(),
                        cleanResultFile.toString(),
                        generatedTestPath
                );

        if (!Files.exists(csvFile)) {

            Files.createDirectories(
                    csvFile.getParent()
            );

            Files.writeString(
                    csvFile,
                    "Project,Bug,Model,Prompt_File,"
                            + "Raw_Response_File,"
                            + "Clean_Response_File,"
                            + "Generated_Test_File\n"
            );
        }

        String existingContent =
                Files.readString(
                        csvFile
                );

        if (existingContent.contains(
                csvRow
        )) {

            System.out.println(
                    "raw_results.csv already contains "
                            + "this result."
            );

            return;
        }

        Files.writeString(
                csvFile,
                existingContent
                        + csvRow
                        + "\n"
        );

        System.out.println(
                "Generation result added to "
                        + csvFile
        );
    }

    /**
     * Process one bug.
     *
     * Any error in one bug is thrown back to the batch loop,
     * which records the failure and continues with the next bug.
     */
    private static void processBug(
            String project,
            String bug
    ) throws Exception {

        Path promptFile =
                Path.of(
                        "Prompt",
                        project,
                        project + "-" + bug,
                        "prompt.txt"
                );

        Path resultDirectory = Path.of("Result", project, project + "-" + bug);
        Path testCodeDirectory = Path.of("TestCode", project, project + "-" + bug);

        Path rawResultFile =
                resultDirectory.resolve(
                        "raw_response.txt"
                );

        Path cleanResultFile =
                resultDirectory.resolve(
                        "response.txt"
                );

        if (Files.exists(cleanResultFile)
        && Files.size(cleanResultFile) > 0) {
    System.out.println(
            "Response already exists. Skipping API call: "
                    + cleanResultFile);
    return;
}
        
        
        if (!Files.exists(promptFile)) {

            throw new IllegalArgumentException(
                    "Prompt file not found: "
                            + promptFile
            );
        }

        Files.createDirectories(
                resultDirectory
        );

        Files.createDirectories(
                testCodeDirectory
        );

        String prompt =
                Files.readString(
                        promptFile
                );

        System.out.println(
                "Prompt: "
                        + promptFile
        );

        System.out.println(
                "Sending request to Claude Sonnet 5.5..."
        );

        ClaudeClient client =
                new ClaudeClient();

        String rawResponse =
                client.sendPrompt(
                        prompt
                );

        Files.writeString(
                rawResultFile,
                rawResponse
        );
if (!rawResponse.contains("\"stop_reason\":\"end_turn\"")) {
    throw new RuntimeException(
            "Response not complete (stop_reason is not end_turn). "
                    + "Check " + rawResultFile);
}
        String cleanResponse =
                extractClaudeText(
                        rawResponse
                );

        Files.writeString(
                cleanResultFile,
                cleanResponse
        );

        System.out.println(
                "Raw response saved to "
                        + rawResultFile
        );

        System.out.println(
                "Clean response saved to "
                        + cleanResultFile
        );

        System.out.println(
                "Extracted response length: "
                        + cleanResponse.length()
                        + " characters"
        );

        String testCode =
                extractTestCode(
                        cleanResponse
                );

        String className =
                findClassName(
                        testCode
                );

        Path testCodeFile =
                testCodeDirectory.resolve(
                        className + ".java"
                );

        Files.writeString(
                testCodeFile,
                testCode
        );

        System.out.println(
                "Generated test saved to "
                        + testCodeFile
        );

        System.out.println(
                "Test class: "
                        + className
        );

        appendRawResult(
                project,
                bug,
                promptFile,
                rawResultFile,
                cleanResultFile,
                testCodeFile
        );
    }

    /**
     * Main entry point.
     *
     * Supported modes:
     *
     * Single bug:
     *     Codec 4
     *
     * Batch:
     *     Codec 1 18
     *
     * The batch mode processes every bug in the specified range.
     * If one bug fails, the program records the error and continues.
     */
    public static void main(
            String[] args
    ) throws Exception {

        if (args.length != 2 && args.length != 3) {

            System.out.println(
                    "Usage:"
            );

            System.out.println(
                    "  Single bug:  <Project> <Bug>"
            );

            System.out.println(
                    "  Batch bugs:  <Project> <StartBug> <EndBug>"
            );

            System.out.println(
                    "Examples:"
            );

            System.out.println(
                    "  Codec 4"
            );

            System.out.println(
                    "  Codec 1 18"
            );

            return;
        }

        String project =
                args[0];

        int startBug;

        int endBug;

        try {

            startBug =
                    Integer.parseInt(
                            args[1]
                    );

            if (args.length == 3) {

                endBug =
                        Integer.parseInt(
                                args[2]
                        );

            } else {

                endBug =
                        startBug;
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Bug number must be an integer."
            );

            return;
        }

        if (startBug > endBug) {

            throw new IllegalArgumentException(
                    "StartBug must be <= EndBug."
            );
        }

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Claude Sonnet 5.5 Batch Generation"
        );

        System.out.println(
                "Project: "
                        + project
        );

        System.out.println(
                "Bug range: "
                        + startBug
                        + " - "
                        + endBug
        );

        System.out.println(
                "========================================"
        );

        int successCount =
                0;

        int failedCount =
                0;

        for (int bugNumber = startBug;
             bugNumber <= endBug;
             bugNumber++) {

            String bug =
                    String.valueOf(
                            bugNumber
                    );

            System.out.println();

            System.out.println(
                    "----------------------------------------"
            );

            System.out.println(
                    "Processing "
                            + project
                            + "-"
                            + bug
            );

            System.out.println(
                    "----------------------------------------"
            );

            try {

                processBug(
                        project,
                        bug
                );

                successCount++;

                System.out.println(
                        "Status: SUCCESS"
                );

            } catch (Exception e) {

                failedCount++;

                System.err.println(
                        "Status: FAILED"
                );

                System.err.println(
                        "Project: "
                                + project
                                + "-"
                                + bug
                );

                System.err.println(
                        "Reason: "
                                + e.getMessage()
                );

                System.err.println(
                        "Skipping to next bug..."
                );
            }
        }

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Batch generation completed."
        );

        System.out.println(
                "Project: "
                        + project
        );

        System.out.println(
                "Successful: "
                        + successCount
        );

        System.out.println(
                "Failed: "
                        + failedCount
        );

        System.out.println(
                "========================================"
        );
    }
}