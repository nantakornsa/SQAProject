Gemini Compress Summary

Model: gemini-3.8-flash per config

| Bug | Detected | Line % | Cond % | Note |
|---|---|---|---|---|
| 1 | YES | 63.0 | 45.8 |  |
| 2 | YES | 68.5 | 46.4 |  |
| 3 | YES | 27.4 | 13.3 |  |
| 4 | YES | 32.7 | 14.6 |  |
| 5 | YES | 51.2 | 33.3 |  |
| 6 | YES | 14.4 | 10.0 |  |
| 7 | YES | 20.3 | 21.1 |  |
| 8 | YES | 38.3 | 35.4 |  |
| 9 | YES | 56.8 | 45.2 |  |
| 10 | - | 0.0 | 0.0 | not measurable (excluded from FDR) |
| 11 | YES | 34.3 | 18.8 |  |
| 12 | YES | 11.5 | 6.4 |  |
| 13 | YES | 10.8 | 3.6 |  |
| 14 | YES | 6.2 | 3.2 |  |
| 15 | YES | 28.1 | 27.3 |  |
| 16 | YES | 38.0 | 20.0 |  |
| 17 | YES | 12.8 | 11.0 |  |
| 18 | YES | 62.7 | 52.3 |  |
| 19 | YES | 40.0 | 25.0 |  |
| 20 | - | - | - | model did not finish (max_tokens) |
| 21 | YES | 46.8 | 35.1 | HELPER:org.apache.commons.compress.AbstractTestCase |
| 22 | YES | 73.7 | 58.9 |  |
| 23 | - | - | - | model did not finish (max_tokens) |
| 24 | YES | 10.7 | 10.3 |  |
| 25 | YES | 32.8 | 14.7 |  |
| 26 | NO | 51.1 | 54.2 | fail on both |
| 27 | YES | 10.3 | 7.9 |  |
| 28 | YES | 22.3 | 13.7 |  |
| 29 | NO | - | - | compile error, check log |
| 30 | YES | 70.9 | 54.7 |  |
| 31 | YES | 14.9 | 11.4 |  |
| 32 | YES | 49.1 | 31.7 |  |
| 33 | YES | 22.2 | 14.1 |  |
| 34 | YES | 21.1 | 0.0 |  |
| 35 | NO | 18.2 | 19.2 | fail on both |
| 36 | YES | 37.2 | 25.2 |  |
| 37 | YES | 17.6 | 8.4 |  |
| 38 | YES | 15.1 | 12.2 |  |
| 39 | YES | 22.7 | 21.4 |  |
| 40 | NO | 90.9 | 86.4 | fail on both |
| 41 | YES | 13.3 | 4.2 |  |
| 42 | YES | 13.5 | 6.8 |  |
| 43 | YES | 32.5 | 20.7 |  |
| 44 | YES | 23.8 | 30.0 |  |
| 45 | NO | - | - | compile error, check log |
| 46 | YES | 5.9 | 4.1 |  |
| 47 | YES | 10.5 | 4.5 |  |

Summary
Total bugs in file: 47
Detected 39 | Not detected 5 | Not measurable 1 | Model did not finish 2 | No test or checkout problem 0
FDR on measured bugs: 39 of 44 = 88.64 percent
FDR if not measurable counted as misses: 39 of 45 = 86.67 percent
FDR if not measurable and unfinished counted as misses: 39 of 47 = 82.98 percent
Avg line coverage 32.0 percent and avg cond coverage 23.4 percent, from 42 measured bugs with coverage
Fault detected means the test fails on buggy and passes on fixed, with both versions compiling.
TODO: check compile errors in logs. Errors in the test itself count as not detected. Errors from helper classes or the script are not measurable: list them in unmeasurable.txt.

Not detected
Compile error, check log: 29, 45
Pass on both buggy and fixed: 
Fail on both: 26, 35, 40

Reported separately
Not measurable: 10
Model did not finish (max_tokens): 20, 23
No test, checkout failed or name collision: 

Notes
Tests not edited or regenerated. Renamed tests (prefix Gemini) keep originals in original_name.
Coverage is measured on the fixed version, only on classes touched by the bug fix, so it is low by design.
Coverage averages exclude not measurable bugs and bugs with no coverage data. A 0.0 percent row can still mean no data: check lines_total in the csv.

Method
Prompts carry an extra EFFICIENCY NOTE that the Claude prompts do not have, because Gemini thinking tokens consumed max_tokens.
max_tokens 4096 for both models, Gemini thinking tokens count inside it.
Measured with Defects4J on Java 11 using gemini_eval2.sh. Claude and Gemini ran different projects so they cannot be compared directly.
