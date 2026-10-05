Gemini Lang Summary

Model: gemini-3.8-flash per config

| Bug | Detected | Line % | Cond % | Note |
|---|---|---|---|---|
| 1 | YES | 12.9 | 5.7 |  |
| 3 | YES | 17.3 | 9.8 |  |
| 4 | YES | 92.0 | 57.1 |  |
| 5 | YES | 20.8 | 21.4 |  |
| 6 | YES | 66.7 | 66.7 |  |
| 7 | YES | 14.2 | 6.6 |  |
| 8 | YES | 43.6 | 31.3 |  |
| 9 | YES | 27.3 | 8.8 |  |
| 10 | YES | 48.0 | 28.5 |  |
| 11 | YES | 13.8 | 8.9 |  |
| 12 | YES | 35.7 | 33.3 |  |
| 13 | YES | 53.2 | 31.2 |  |
| 14 | YES | 0.4 | 0.5 |  |
| 15 | YES | 16.1 | 11.3 |  |
| 16 | YES | 6.7 | 2.5 |  |
| 17 | YES | 70.0 | 50.0 |  |
| 19 | YES | 75.0 | 57.9 |  |
| 20 | NO | 2.8 | 0.8 | pass on both |
| 21 | YES | 2.2 | 2.8 |  |
| 22 | YES | 18.4 | 12.2 |  |
| 23 | NO | 63.8 | 43.2 | fail on both |
| 24 | YES | 12.0 | 8.9 |  |
| 26 | YES | 28.0 | 21.9 |  |
| 27 | YES | 8.5 | 3.4 |  |
| 28 | YES | 72.0 | 56.2 |  |
| 29 | YES | 83.7 | 58.0 |  |
| 30 | YES | 2.6 | 2.9 |  |
| 31 | YES | 1.4 | 1.6 |  |
| 32 | YES | 36.8 | 26.0 |  |
| 33 | YES | 13.3 | 2.8 |  |
| 34 | YES | 13.6 | 1.4 |  |
| 35 | YES | 2.7 | 0.5 |  |
| 36 | YES | 17.6 | 9.9 |  |
| 37 | YES | 3.2 | 0.4 |  |
| 38 | YES | 34.4 | 29.8 |  |
| 39 | YES | 4.7 | 4.2 |  |
| 40 | YES | 0.5 | 0.3 |  |
| 41 | YES | 21.3 | 10.2 |  |
| 42 | YES | 22.2 | 11.8 |  |
| 43 | YES | 76.6 | 55.6 |  |
| 44 | YES | 2.1 | 1.8 |  |
| 45 | YES | 9.3 | 7.7 |  |
| 46 | YES | 11.7 | 11.9 |  |
| 47 | YES | 5.1 | 2.8 |  |
| 49 | YES | 11.2 | 4.3 |  |
| 50 | YES | 43.7 | 36.8 |  |
| 51 | YES | 4.1 | 2.4 |  |
| 52 | YES | 11.2 | 12.9 |  |
| 53 | YES | 19.4 | 17.1 |  |
| 54 | YES | 19.8 | 16.7 |  |
| 55 | YES | 37.9 | 32.1 |  |
| 56 | YES | 26.3 | 23.3 |  |
| 57 | YES | 8.9 | 0.0 |  |
| 58 | YES | 11.1 | 6.0 |  |
| 59 | YES | 2.3 | 1.1 |  |
| 60 | YES | 7.5 | 5.6 |  |
| 61 | YES | 6.1 | 4.1 |  |
| 62 | YES | 25.5 | 18.1 |  |
| 63 | YES | 41.0 | 25.6 |  |
| 64 | YES | 24.2 | 21.4 |  |
| 65 | YES | 20.3 | 13.1 |  |

Summary
Total bugs in file: 61
Detected 59 | Not detected 2 | Not measurable 0 | Model did not finish 0 | No test or checkout problem 0
FDR on measured bugs: 59 of 61 = 96.72 percent
FDR if not measurable counted as misses: 59 of 61 = 96.72 percent
FDR if not measurable and unfinished counted as misses: 59 of 61 = 96.72 percent
Avg line coverage 24.7 percent and avg cond coverage 17.4 percent, from 61 measured bugs with coverage
Fault detected means the test fails on buggy and passes on fixed, with both versions compiling.
TODO: check compile errors in logs. Errors in the test itself count as not detected. Errors from helper classes or the script are not measurable: list them in unmeasurable.txt.

Not detected
Compile error, check log: 
Pass on both buggy and fixed: 20
Fail on both: 23

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
