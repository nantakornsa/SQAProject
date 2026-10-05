package com.example.claudesonnet;

import java.nio.file.Files;
import java.nio.file.Path;

/** Re-extract test code from saved response.txt (no API call). Usage: <Project> <Bug> [<Bug> ...] */
public class ReprocessResponse {
    public static void main(String[] args) throws Exception {
        String project = args[0];
        for (int i = 1; i < args.length; i++) {
            String bug = args[i];
            Path dir = Path.of("Result", project, project + "-" + bug);
            Path testDir = Path.of("TestCode", project, project + "-" + bug);
            try {
                String clean = Files.readString(dir.resolve("response.txt"));
                String code = ClaudeClient.extractTestCode(clean);
                String cls = ClaudeClient.findClassName(code);
                Files.createDirectories(testDir);
                Files.writeString(testDir.resolve(cls + ".java"), code);
                System.out.println(project + "-" + bug + ": OK -> " + cls);
            } catch (Exception e) {
                System.out.println(project + "-" + bug + ": FAILED -> " + e.getMessage());
            }
        }
    }
}
