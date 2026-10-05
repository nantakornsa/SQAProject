Gemini JacksonCore Summary

Model: gemini-3.8-flash per config

| Bug | Detected | Line % | Cond % | Note |
|---|---|---|---|---|
| 1 | YES | 6.0 | 1.7 | |
| 2 | YES | 6.1 | 3.5 | helper BaseTest |
| 3 | NO | - | - | test compile error |
| 4 | YES | 11.0 | 4.5 | |
| 5 | YES | 36.9 | 21.0 | |
| 6 | YES | 33.7 | 18.6 | |
| 7 | YES | 13.9 | 0.0 | helper BaseTest |
| 8 | YES | 12.9 | 11.4 | |
| 9 | NO | - | - | test compile error |
| 10 | NO | 50.1 | 32.7 | pass on both |
| 11 | NO | 39.2 | 21.6 | pass on both, helper BaseTest |
| 12 | NO | 4.0 | 2.7 | fail on both |
| 13 | YES | 34.3 | 21.4 | helper BaseTest |
| 14 | YES | 36.8 | 31.8 | |
| 15 | NO | - | - | test compile error |
| 16 | NO | - | - | test compile error |
| 17 | YES | 10.1 | 7.4 | |
| 18 | NO | - | - | test compile error |
| 19 | YES | 7.2 | 4.7 | |
| 20 | YES | 12.0 | 8.9 | helper BaseTest |
| 21 | YES | 22.8 | 17.3 | helper BaseTest |
| 22 | NO | - | - | test compile error |
| 23 | YES | 17.6 | 2.4 | helper BaseTest |
| 24 | YES | 14.4 | 6.8 | helper BaseTest |
| 25 | NO | 9.5 | 5.1 | pass on both |
| 26 | NO | - | - | test compile error |

Summary
Detected 15 of 26 = 57.69 percent
Avg line coverage 19.9 percent and avg cond coverage 11.8 percent, from 19 bugs with coverage

Not detected
Test compile error: 3, 9, 15, 16, 18, 22, 26
Pass on both buggy and fixed: 10, 11, 25
Fail on both: 12

Notes
Tests not edited or regenerated. Compile errors in tests count as not detected.
Tests 12, 16, 25 renamed with prefix Gemini because of class name collision, originals kept in original_name.
13 tests extend helper classes. Helpers and testsupport classes were packed into the archive and their failures filtered out.
Coverage is measured only on classes touched by the bug fix, so it is low by design.

Method
Prompts carry an extra EFFICIENCY NOTE that the Claude prompts do not have, because Gemini thinking tokens consumed max_tokens.
max_tokens 4096 for both models, Gemini thinking tokens count inside it.
Prompt template is fixed code and includes the diff of the fixed version.
Claude and Gemini ran different projects so they cannot be compared directly.
