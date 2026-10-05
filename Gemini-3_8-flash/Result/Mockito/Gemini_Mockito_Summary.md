Gemini Mockito Summary

Model: gemini-3.8-flash per config, raw_response model google/gemini-3.8-flash

| Bug | Detected | Line % | Cond % | Note |
|---|---|---|---|---|
| 1 | YES | 42.0 | 26.7 |  |
| 2 | YES | 46.2 | 12.5 |  |
| 3 | YES | 58.6 | 41.1 |  |
| 4 | YES | 4.4 | 0.0 |  |
| 5 | YES | 0.0 | 0.0 |  |
| 6 | YES | 5.5 | 0.0 |  |
| 7 | NO | 37.1 | 24.4 | pass on both |
| 8 | YES | 42.0 | 35.4 |  |
| 9 | YES | 100.0 | 100.0 |  |
| 10 | YES | 79.5 | 37.5 |  |
| 11 | YES | 52.6 | 40.0 |  |
| 12 | YES | 77.8 | 50.0 | helper TestBase |
| 13 | NO | - | - | not measurable, helper IMethods, helper TestBase |
| 14 | NO | - | - | not measurable, helper IMethods, helper TestBase |
| 15 | YES | 78.6 | 75.0 |  |
| 16 | YES | 21.2 | 10.7 | helper TestBase |
| 17 | NO | - | - | not measurable, helper IMethods, helper TestBase |
| 18 | YES | 13.0 | 5.0 |  |
| 19 | YES | 85.9 | 66.7 |  |
| 20 | YES | 51.2 | 20.0 |  |
| 21 | YES | 50.0 | 58.3 |  |
| 22 | YES | 13.3 | 3.8 | helper TestBase |
| 23 | YES | 90.2 | 66.7 |  |
| 24 | YES | 13.6 | 7.9 |  |
| 25 | YES | 81.2 | 37.5 |  |
| 26 | NO | - | - | test compile error |
| 27 | YES | 82.9 | 45.5 | helper TestBase |
| 28 | YES | 87.1 | 80.0 |  |
| 29 | YES | 86.7 | 50.0 | helper TestBase |
| 30 | YES | 17.0 | 28.6 |  |
| 31 | YES | 80.0 | 50.0 |  |
| 32 | YES | 76.9 | 66.7 | helper TestBase |
| 33 | NO | 41.5 | 21.4 | pass on both, helper TestBase |
| 34 | NO | 0.0 | 0.0 | fail on both, helper TestBase |
| 35 | NO | 9.8 | 0.0 | fail on both |
| 36 | NO | 26.4 | 15.9 | fail on both, helper TestBase |
| 37 | NO | - | - | test compile error |
| 38 | YES | 72.2 | 50.0 | helper TestBase |

Summary
Detected 28 of 35 measurable bugs = 80.00 percent. If the 3 unmeasurable bugs count as misses, 28 of 38 = 73.68 percent.
Avg line coverage 49.2 percent and avg cond coverage 34.2 percent, from 33 bugs with coverage.
Fault detected means the test fails on buggy and passes on fixed, with both versions compiling.

Not detected
Pass on both buggy and fixed: 7, 33
Fail on both: 34, 35, 36
Test compile error: 26 because Primitives.primitiveValueOf does not exist, 37 because when is called on a void method

Not measurable
13, 14, 17: the test imports org.mockitousage.IMethods, which the measuring script did not pack into the archive. This is a script limitation, not a wrong test, so it is reported separately from not detected.

Notes
Tests not edited or regenerated. Compile errors in tests count as not detected. Compile errors caused by helper classes or the script count as not measurable.
18 tests renamed with prefix Gemini because of class name collision: bugs 4, 7, 10, 11, 13, 14, 15, 16, 21, 22, 23, 27, 28, 32, 34, 36, 37, 38. Originals kept in original_name.
Bugs 5, 12, 13, 14, 16, 17, 22, 27, 29, 32, 33, 34, 36, 38 were measured again after the script patch that skips ExtraMatchers.java. The first round could not measure them because of TestBase. The pre-redo file is kept as gemini_final_results_merged_before_redo2.csv.
Averages include rows with 0.0 percent coverage, at least Mockito-5 which is unexplained and Mockito-34.
Coverage is measured only on classes touched by the bug fix, so it is low by design.

Method
Prompts carry an extra EFFICIENCY NOTE that the Claude prompts do not have, because Gemini thinking tokens consumed max_tokens.
max_tokens 4096 for both models, Gemini thinking tokens count inside it.
findJavaFile in PromptGenerator was patched to match the package path and to find tests under /test/.
The measuring script packs helper classes that tests depend on: org.mockitoutil, StateMaster, ConfigurationAccess, MockitoConfiguration, SmartMock. It filters failures that come from helpers and skips ExtraMatchers.java because it does not compile on Java 11.
Coverage runs twice per bug with Defects4J on Java 11.
Claude and Gemini ran different projects so they cannot be compared directly.
