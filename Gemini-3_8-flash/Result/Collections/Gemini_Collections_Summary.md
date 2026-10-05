Gemini Collections Summary

Model: google/gemini-3.8-flash from raw_response

| Bug | Detected | Line % | Cond % | Note |
|---|---|---|---|---|
| 1 | YES | 17.6 | 12.5 | |
| 2 | YES | 3.4 | 2.2 | |
| 3 | YES | 0.9 | 0.0 | |
| 4 | YES | 26.8 | 16.7 | |
| 5 | NO | - | - | test compile error |
| 6 | YES | 13.1 | 8.5 | |
| 7 | YES | 9.4 | 5.4 | |
| 8 | YES | 42.2 | 22.2 | |
| 9 | YES | 8.4 | 5.0 | |
| 10 | YES | 23.5 | 10.4 | |
| 11 | YES | 56.8 | 58.3 | |
| 12 | YES | 12.2 | 7.2 | |
| 13 | YES | 4.7 | 2.5 | |
| 14 | YES | 33.3 | 75.0 | |
| 15 | YES | 23.3 | 31.8 | |
| 16 | YES | 23.2 | 25.0 | |
| 17 | YES | 62.5 | 33.3 | |
| 18 | NO | 18.4 | 15.0 | pass on both buggy and fixed |
| 19 | YES | 15.3 | 15.0 | |
| 20 | YES | 52.1 | 40.9 | |
| 21 | YES | 22.9 | 18.4 | |
| 22 | YES | 14.7 | 11.5 | |
| 23 | YES | 24.3 | 75.0 | |
| 24 | YES | 20.7 | 14.3 | |
| 25 | YES | 6.5 | 3.8 | |
| 26 | YES | 56.8 | 58.3 | |
| 27 | YES | 13.7 | 7.4 | |
| 28 | YES | 36.6 | 28.8 | |

Summary
Detected 26 of 28 = 92.86 percent
Avg line coverage 23.8 percent and avg cond coverage 22.4 percent, from 27 bugs with coverage

Not detected
Test compile error: 5, calls SetUniqueList.decorate with two arguments but this version takes one
Pass on both buggy and fixed: 18

Notes
Tests were not edited or regenerated. A compile error in a test counts as not detected.
Coverage is measured only on classes touched by the bug fix, so it is low by design.

Method
Prompts carry an extra EFFICIENCY NOTE that the Claude prompts do not have, because Gemini thinking tokens consumed max_tokens.
max_tokens 4096 for both models, Gemini thinking tokens count inside it.
Prompt template is fixed code and includes the diff of the fixed version.
Claude and Gemini ran different projects so they cannot be compared directly.
Collections-8 prompt first failed with an empty source diff because two files named UnboundedFifoBuffer.java exist. findJavaFile in the Gemini folder was patched to match the package path when looking for source.
