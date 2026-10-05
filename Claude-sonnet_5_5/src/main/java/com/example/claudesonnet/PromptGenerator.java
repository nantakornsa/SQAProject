package com.example.claudesonnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PromptGenerator {

    private static final String DEFECTS4J =
            "/Users/suphawat/Documents/SQA/defect4j_test/"
                    + "defects4j/framework/bin/defects4j";

    private static final String JAVA_HOME_11 =
            "/opt/homebrew/opt/openjdk@11";

    private static final String PROJECT_ROOT =
            "/Users/suphawat/Documents/SQA/FinalProject-Test/"
                    + "SQAProject/Claude-sonnet_5_5";

    private static final String DEFECT4J_ROOT =
            "/Users/suphawat/Documents/SQA/defect4j_test";

    public String getBugInfo(
            String project,
            String bug
    ) throws Exception {

        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        DEFECTS4J,
                        "info",
                        "-p",
                        project,
                        "-b",
                        bug
                );

        processBuilder.environment().put(
                "JAVA_HOME",
                JAVA_HOME_11
        );

        String currentPath =
                processBuilder.environment().get("PATH");

        processBuilder.environment().put(
                "PATH",
                JAVA_HOME_11 + "/bin:" + currentPath
        );

        processBuilder.redirectErrorStream(true);

        Process process =
                processBuilder.start();

        StringBuilder output =
                new StringBuilder();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                process.getInputStream()
                        )
                );

        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line);
            output.append(System.lineSeparator());
        }

        int exitCode =
                process.waitFor();

        if (exitCode != 0) {

            System.out.println(output);

            throw new RuntimeException(
                    "Defects4J info failed for "
                            + project
                            + "-"
                            + bug
            );
        }

        return output.toString();
    }

    private String getCheckoutPath(
            String project,
            String bug
    ) {

        return DEFECT4J_ROOT + "/checkouts/" + project + "/" + project + "-" + bug + "b";
    }

    /**
     * Checkout a Defects4J buggy version
     * if it does not already exist.
     */
    private void checkoutBug(
            String project,
            String bug
    ) throws Exception {

        String checkoutPath =
                getCheckoutPath(
                        project,
                        bug
                );

        Path path =
                Paths.get(checkoutPath);

        if (Files.exists(path)) {

            System.out.println(
                    "Checkout already exists."
            );

            System.out.println(
                    "Using: "
                            + checkoutPath
            );

            return;
        }

        System.out.println(
                "Checkout not found."
        );

        System.out.println(
                "Creating: "
                        + checkoutPath
        );

        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        DEFECTS4J,
                        "checkout",
                        "-p",
                        project,
                        "-v",
                        bug + "b",
                        "-w",
                        checkoutPath
                );

        processBuilder.environment().put(
                "JAVA_HOME",
                JAVA_HOME_11
        );

        String currentPath =
                processBuilder.environment().get("PATH");

        processBuilder.environment().put(
                "PATH",
                JAVA_HOME_11 + "/bin:" + currentPath
        );

        processBuilder.redirectErrorStream(true);

        Process process =
                processBuilder.start();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                process.getInputStream()
                        )
                );

        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode =
                process.waitFor();

        if (exitCode != 0) {

            throw new RuntimeException(
                    "Defects4J checkout failed for "
                            + project
                            + "-"
                            + bug
            );
        }

        System.out.println(
                "Checkout completed: "
                        + checkoutPath
        );
    }

    /**
     * Extract the first triggering test from
     * Defects4J bug information.
     */
    private String extractFirstTriggeringTest(
            String bugInfo
    ) {

        String[] lines =
                bugInfo.split("\\R");

        for (String line : lines) {

            String trimmed =
                    line.trim();

            if (!trimmed.startsWith("- ")) {
                continue;
            }

            String test =
                    trimmed.substring(2).trim();

            int separator =
                    test.indexOf("::");

            if (separator <= 0) {
                continue;
            }

            return test;
        }

        throw new RuntimeException(
                "No triggering test found"
        );
    }

    /**
     * Extract ALL modified source class names
     * from Defects4J bug information.
     */
    private List<String> extractModifiedSources(
            String bugInfo
    ) {

        String[] lines =
                bugInfo.split("\\R");

        List<String> sources =
                new ArrayList<>();

        boolean insideModifiedSources =
                false;

        for (String line : lines) {

            String trimmed =
                    line.trim();

            if (trimmed.equals(
                    "List of modified sources:"
            )) {

                insideModifiedSources = true;

                continue;
            }

            if (insideModifiedSources) {

                if (trimmed.startsWith("- ")) {

                    String source =
                            trimmed
                                    .substring(2)
                                    .trim();

                    sources.add(source);

                } else if (!trimmed.isEmpty()) {

                    break;
                }
            }
        }

        if (sources.isEmpty()) {

            throw new RuntimeException(
                    "No modified sources found"
            );
        }

        return sources;
    }

    private String extractFixedRevision(
            String bugInfo
    ) {

        String[] lines =
                bugInfo.split("\\R");

        for (String line : lines) {

            String trimmed =
                    line.trim();

            if (trimmed.matches(
                    "[0-9a-f]{40}"
            )) {

                return trimmed;
            }
        }

        return "NUMERIC_REVISION";
    }

    /**
     * Find a Java source/test file by class name.
     */
    private Path findJavaFile(
            String checkoutPath,
            String className,
            boolean testFile
    ) throws Exception {

        String simpleName =
                className;

        int separator =
                simpleName.lastIndexOf('.');

        if (separator >= 0) {

            simpleName =
                    simpleName.substring(
                            separator + 1
                    );
        }

        String fileName =
                simpleName + ".java";

        Path checkoutDirectory =
                Paths.get(checkoutPath);

        try (Stream<Path> paths =
                     Files.walk(checkoutDirectory)) {

            return paths
                    .filter(Files::isRegularFile)
                    .filter(path ->
                            path.getFileName()
                                    .toString()
                                    .equals(fileName)
                    )
                    .filter(path -> {

                        if (!testFile) {
                            String wantedPath = className.replace('.', '/') + ".java";
                            boolean pkgMatch = className.indexOf('.') < 0 || path.toString().endsWith("/" + wantedPath);
                            return pkgMatch && !path.toString().contains("/JodaTimeContrib/");
                        }

                        return path.toString()
                                .contains("/test/") || path.toString().contains("/tests/");
                    })
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * Extract a single test method from
     * the existing project test source.
     */
    private String extractTestMethod(
            String testFile,
            String testMethodName
    ) {

        String[] lines =
                testFile.split("\\R");

        StringBuilder method =
                new StringBuilder();

        boolean insideMethod =
                false;

        int braceCount = 0;

        for (String line : lines) {

            String trimmed =
                    line.trim();

            if (!insideMethod) {

                if (trimmed.contains(
                        testMethodName + "("
                )) {

                    insideMethod = true;

                    method.append(line)
                            .append(
                                    System.lineSeparator()
                            );

                    braceCount +=
                            countCharacter(
                                    line,
                                    '{'
                            );

                    braceCount -=
                            countCharacter(
                                    line,
                                    '}'
                            );

                    if (braceCount <= 0) {
                        return method.toString();
                    }
                }

                continue;
            }

            method.append(line)
                    .append(
                            System.lineSeparator()
                    );

            braceCount +=
                    countCharacter(
                            line,
                            '{'
                    );

            braceCount -=
                    countCharacter(
                            line,
                            '}'
                    );

            if (braceCount <= 0) {
                return method.toString();
            }
        }

        return testFile;
    }

    private int countCharacter(
            String text,
            char target
    ) {

        int count = 0;

        for (int i = 0;
             i < text.length();
             i++) {

            if (text.charAt(i) == target) {
                count++;
            }
        }

        return count;
    }

    /**
     * Generate source diffs for ALL modified sources
     * that actually exist in the checkout.
     */
    private String getSourceDiff(
            String project,
            String bug,
            String bugInfo
    ) throws Exception {

        String fixedRevision =
                extractFixedRevision(
                        bugInfo
                );

        List<String> modifiedSources =
                extractModifiedSources(
                        bugInfo
                );

        String checkoutPath =
                getCheckoutPath(
                        project,
                        bug
                );

        Path checkoutDirectory =
                Paths.get(checkoutPath);

        StringBuilder allDiffs =
                new StringBuilder();

        int foundSourceCount = 0;

        for (String sourceClass :
                modifiedSources) {

            Path sourcePath =
                    findJavaFile(
                            checkoutPath,
                            sourceClass,
                            false
                    );

            if (sourcePath == null) {

                System.out.println(
                        "Modified source not found "
                                + "in checkout: "
                                + sourceClass
                );

                continue;
            }

            foundSourceCount++;

            String relativeSourcePath =
                    checkoutDirectory
                            .relativize(sourcePath)
                            .toString();

            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "git",
                            "-C",
                            checkoutPath,
                            "diff",
                            "HEAD",
                            (fixedRevision.equals("NUMERIC_REVISION") ? "D4J_" + project + "_" + bug + "_FIXED_VERSION" : fixedRevision),
                            "--",
                            relativeSourcePath
                    );

            processBuilder.redirectErrorStream(true);

            Process process =
                    processBuilder.start();

            StringBuilder output =
                    new StringBuilder();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                output.append(line)
                        .append(
                                System.lineSeparator()
                        );
            }

            int exitCode =
                    process.waitFor();

            if (exitCode != 0) {

                throw new RuntimeException(
                        "Unable to get source diff for "
                                + sourceClass
                );
            }

            String diff =
                    output.toString();

            if (!diff.trim().isEmpty()) {

                allDiffs.append(
                        "=== MODIFIED SOURCE: "
                );

                allDiffs.append(
                        sourceClass
                );

                allDiffs.append(
                        " ===\n"
                );

                allDiffs.append(
                        diff
                );

                allDiffs.append(
                        "\n"
                );
            }
        }

        if (foundSourceCount == 0) {

            throw new RuntimeException(
                    "None of the modified source files "
                            + "were found in checkout."
            );
        }

        if (allDiffs.toString()
                .trim()
                .isEmpty()) {

            throw new RuntimeException(
                    "Source diff is empty for "
                            + project
                            + "-"
                            + bug
            );
        }

        return allDiffs.toString();
    }

    public Path generatePrompt(
            String project,
            String bug
    ) throws Exception {

        String bugInfo =
                getBugInfo(
                        project,
                        bug
                );

        String triggeringTest =
                extractFirstTriggeringTest(
                        bugInfo
                );

        String testClass =
                triggeringTest.substring(
                        0,
                        triggeringTest.indexOf("::")
                );

        String testMethod =
                triggeringTest.substring(
                        triggeringTest.indexOf("::") + 2
                );

        String checkoutPath =
                getCheckoutPath(
                        project,
                        bug
                );

        Path testPath =
                findJavaFile(
                        checkoutPath,
                        testClass,
                        true
                );

        if (testPath == null) {

            throw new RuntimeException(
                    "Java test file not found: "
                            + testClass
            );
        }

        String testFile =
                GeminiIo.readString(
                        testPath
                );

        String testMethodCode =
                extractTestMethod(
                        testFile,
                        testMethod
                );

        String sourceDiff =
                getSourceDiff(
                        project,
                        bug,
                        bugInfo
                );

        String prompt =
                "You are an expert Java testing engineer.\n"
                + "\n"
                + "Generate a Java regression test for "
                + "the following Defects4J bug.\n"
                + "\n"
                + "Requirements:\n"
                + "1. The test must fail on the buggy version.\n"
                + "2. The test must pass on the fixed version.\n"
                + "3. Do not modify production code.\n"
                + "4. Follow the project's existing test framework "
                + "and coding style.\n"
                + "5. Return complete compilable Java test code only.\n"
                + "\n"
                + "=== BUG INFORMATION ===\n"
                + bugInfo
                + "\n"
                + "=== BUGGY/FIXED SOURCE DIFF ===\n"
                + sourceDiff
                + "\n"
                + "=== TRIGGERING TEST METHOD ===\n"
                + testMethodCode
                + "\n"
                + "=== OUTPUT ===\n"
                + "=== TEST CODE ===\n"
                + "```java\n"
                + "<complete Java test code>\n"
                + "```\n";

        Path outputDirectory =
                Paths.get(
                        PROJECT_ROOT,
                        "Prompt",
                        project,
                        project + "-" + bug
                );

        Files.createDirectories(
                outputDirectory
        );

        Path outputFile =
                outputDirectory.resolve(
                        "prompt.txt"
                );

        Files.writeString(
                outputFile,
                prompt
        );

        return outputFile;
    }

    private static void processBug(
            PromptGenerator generator,
            String project,
            String bug
    ) throws Exception {

        generator.checkoutBug(
                project,
                bug
        );

        Path prompt =
                generator.generatePrompt(
                        project,
                        bug
                );

        System.out.println(
                "Prompt generated: "
                        + prompt
        );
    }

    public static void main(
            String[] args
    ) throws Exception {

        if (args.length != 2
                && args.length != 3) {

            System.out.println("Usage:");

            System.out.println(
                    "  Single bug: <Project> <Bug>"
            );

            System.out.println(
                    "  Batch bugs: <Project> "
                            + "<StartBug> <EndBug>"
            );

            System.out.println();

            System.out.println("Examples:");

            System.out.println(
                    "  Codec 4"
            );

            System.out.println(
                    "  Codec 5 18"
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
                "Defects4J Prompt Batch Generator"
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

        PromptGenerator generator =
                new PromptGenerator();

        int successCount = 0;
        int failedCount = 0;

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

                /*
                 * Do not overwrite existing prompts.
                 */
                Path existingPrompt =
                        Paths.get(
                                PROJECT_ROOT,
                                "Prompt",
                                project,
                                project + "-" + bug,
                                "prompt.txt"
                        );

                if (Files.exists(
                        existingPrompt
                )) {

                    System.out.println(
                            "Prompt already exists."
                    );

                    System.out.println(
                            "Skipping: "
                                    + existingPrompt
                    );

                    successCount++;

                    continue;
                }

                processBug(
                        generator,
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
                "Prompt batch generation completed."
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