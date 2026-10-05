Gemini Chart Summary

Model: gemini-3.8-flash per config

| Bug | Detected | Line % | Cond % | Note |
|---|---|---|---|---|
| 1 | YES | 5.9 | 4.1 |  |
| 2 | YES | 5.0 | 3.8 |  |
| 3 | YES | 20.9 | 14.0 |  |
| 4 | NO | 11.0 | 5.9 | fail on both |
| 5 | YES | 20.6 | 16.2 |  |
| 6 | YES | 32.4 | 31.2 |  |
| 7 | YES | 47.2 | 46.3 |  |
| 8 | NO | 24.7 | 7.4 | pass on both |
| 9 | NO | - | - | compile error, check log |
| 10 | YES | 100.0 | 0.0 |  |
| 11 | YES | 8.1 | 6.9 |  |
| 12 | YES | 15.3 | 7.1 |  |
| 13 | NO | - | - | compile error, check log |
| 14 | NO | - | - | compile error, check log |
| 15 | YES | 9.1 | 1.6 |  |
| 16 | YES | 17.6 | 7.0 |  |
| 17 | YES | 11.1 | 7.8 |  |
| 18 | YES | 12.2 | 7.1 |  |
| 19 | YES | 12.0 | 5.5 |  |
| 20 | YES | 18.2 | 0.0 |  |
| 21 | YES | 37.4 | 35.4 |  |
| 22 | YES | 27.6 | 17.6 |  |
| 23 | YES | 26.3 | 19.6 |  |
| 24 | YES | 36.0 | 10.0 |  |
| 25 | YES | 61.0 | 35.6 |  |
| 26 | YES | 39.3 | 14.6 |  |

Summary
Total bugs in file: 26
Detected 21 | Not detected 5 | Not measurable 0 | Model did not finish 0 | No test or checkout problem 0
FDR on measured bugs: 21 of 26 = 80.77 percent
FDR if not measurable counted as misses: 21 of 26 = 80.77 percent
FDR if not measurable and unfinished counted as misses: 21 of 26 = 80.77 percent
Avg line coverage 26.0 percent and avg cond coverage 13.2 percent, from 23 measured bugs with coverage
Fault detected means the test fails on buggy and passes on fixed, with both versions compiling.
TODO: check compile errors in logs. Errors in the test itself count as not detected. Errors from helper classes or the script are not measurable: list them in unmeasurable.txt.

Not detected
Compile error, check log: 9, 13, 14
Pass on both buggy and fixed: 8
Fail on both: 4

Reported separately
Not measurable: 
Model did not finish (max_tokens): 
No test, checkout failed or name collision: 

Notes
Tests not edited or regenerated. Renamed tests (prefix Gemini) keep originals in original_name.
Coverage is measured on the fixed version, only on classes touched by the bug fix, so it is low by design.
Coverage averages exclude not measurable bugs and bugs with no coverage data. A 0.0 percent row can still mean no data: check lines_total in the csv.

Method
Prompts carry an extra EFFICIENCY NOTE that the Claude prompts do not have, because Gemini thinking tokens consumed max_tokens.
max_tokens 4096 for both models, Gemini thinking tokens count inside it.
Measured with Defects4J on Java 11 using gemini_eval2.sh. Claude and Gemini ran different projects so they cannot be compared directly.
