# Claude Sonnet 5.5 – Defects4J Cli (39 bugs)

Model: claude-sonnet-5.5 (KKU IntelSphere API) | Benchmark: Defects4J Cli 1-5, 7-40 (bug 6 does not exist in this version)

## Results

| Bug | Buggy (compile/test) | Fixed (compile/test) | Fault Detected | Line Coverage | Condition Coverage |
|---|---|---|---|---|---|
| Cli-1 | PASS/FAIL | PASS/PASS | YES | 19/45 (42.2%) | 6/16 (37.5%) |
| Cli-2 | PASS/FAIL | PASS/PASS | YES | 35/69 (50.7%) | 13/42 (31.0%) |
| Cli-3 | PASS/FAIL | PASS/PASS | YES | 8/59 (13.6%) | 6/24 (25.0%) |
| Cli-4 | PASS/FAIL | PASS/PASS | YES | 26/101 (25.7%) | 10/70 (14.3%) |
| Cli-5 | PASS/FAIL | PASS/PASS | YES | 7/13 (53.8%) | 6/10 (60.0%) |
| Cli-7 | PASS/FAIL | PASS/PASS | YES | 57/68 (83.8%) | 16/27 (59.3%) |
| Cli-8 | FAIL/NOT_RUN | FAIL/NOT_RUN | NO | n/a (test did not compile) | n/a |
| Cli-9 | PASS/FAIL | PASS/PASS | YES | 31/106 (29.2%) | 10/70 (14.3%) |
| Cli-10 | PASS/FAIL | PASS/PASS | YES | 50/106 (47.2%) | 20/70 (28.6%) |
| Cli-11 | PASS/FAIL | PASS/PASS | YES | 50/198 (25.3%) | 17/106 (16.0%) |
| Cli-12 | PASS/FAIL | PASS/PASS | YES | 16/28 (57.1%) | 9/20 (45.0%) |
| Cli-13 | PASS/FAIL | PASS/PASS | YES | 74/197 (37.6%) | 19/118 (16.1%) |
| Cli-14 | PASS/FAIL | PASS/PASS | YES | 58/189 (30.7%) | 23/128 (18.0%) |
| Cli-15 | PASS/FAIL | PASS/PASS | YES | 48/98 (49.0%) | 20/54 (37.0%) |
| Cli-16 | PASS/FAIL | PASS/PASS | YES | 155/351 (44.2%) | 65/222 (29.3%) |
| Cli-17 | PASS/FAIL | PASS/PASS | YES | 40/68 (58.8%) | 16/42 (38.1%) |
| Cli-18 | PASS/FAIL | PASS/PASS | YES | 28/65 (43.1%) | 11/42 (26.2%) |
| Cli-19 | PASS/FAIL | PASS/PASS | YES | 38/64 (59.4%) | 18/42 (42.9%) |
| Cli-20 | PASS/FAIL | PASS/PASS | YES | 29/68 (42.6%) | 10/48 (20.8%) |
| Cli-21 | PASS/FAIL | PASS/PASS | YES | 137/309 (44.3%) | 53/196 (27.0%) |
| Cli-22 | PASS/FAIL | PASS/PASS | YES | 33/59 (55.9%) | 18/44 (40.9%) |
| Cli-23 | PASS/FAIL | PASS/FAIL | NO | 83/197 (42.1%) | 31/110 (28.2%) |
| Cli-24 | PASS/FAIL | PASS/PASS | YES | 101/198 (51.0%) | 44/112 (39.3%) |
| Cli-25 | PASS/FAIL | PASS/PASS | YES | 101/198 (51.0%) | 44/112 (39.3%) |
| Cli-26 | PASS/FAIL | PASS/PASS | YES | 28/63 (44.4%) | 0/4 (0.0%) |
| Cli-27 | PASS/FAIL | PASS/PASS | YES | 13/34 (38.2%) | 3/12 (25.0%) |
| Cli-28 | PASS/FAIL | PASS/PASS | YES | 38/101 (37.6%) | 17/68 (25.0%) |
| Cli-29 | PASS/FAIL | PASS/PASS | YES | 10/12 (83.3%) | 13/14 (92.9%) |
| Cli-31 | PASS/FAIL | PASS/PASS | YES | 117/385 (30.4%) | 23/212 (10.8%) |
| Cli-32 | PASS/FAIL | PASS/PASS | YES | 39/201 (19.4%) | 18/114 (15.8%) |
| Cli-33 | PASS/FAIL | PASS/PASS | YES | 75/211 (35.5%) | 23/118 (19.5%) |
| Cli-34 | PASS/FAIL | PASS/PASS | YES | 83/181 (45.9%) | 12/88 (13.6%) |
| Cli-35 | PASS/FAIL | PASS/PASS | YES | 26/59 (44.1%) | 8/22 (36.4%) |
| Cli-36 | PASS/FAIL | PASS/PASS | YES | 25/94 (26.6%) | 5/36 (13.9%) |
| Cli-37 | PASS/FAIL | PASS/PASS | YES | 71/191 (37.2%) | 43/158 (27.2%) |
| Cli-38 | PASS/FAIL | PASS/PASS | YES | 85/193 (44.0%) | 55/164 (33.5%) |
| Cli-39 | PASS/FAIL | PASS/PASS | YES | 10/45 (22.2%) | 7/20 (35.0%) |
| Cli-40 | PASS/FAIL | PASS/PASS | YES | 10/45 (22.2%) | 9/20 (45.0%) |

## Summary

- Fault Detection Rate: 36/39 = 92.31%
- Average Line Coverage: 42.4% (over 37 bugs whose test compiled)
- Average Condition Coverage: 30.5% (over 37 bugs whose test compiled)

## Not detected

- Cli-8: the Claude test does not compile (references HelpFormatter.DEFAULT_NEW_LINE_PLACEHOLDER_FIX, which does not exist).
- Cli-30: the Claude test does not compile (passes Properties where a boolean is expected).
- Cli-23: the Claude test fails on the fixed version with java.lang.OutOfMemoryError: Java heap space.
- Generated tests were not modified. Cli-8 and Cli-30 have no coverage value (not 0%).

## Notes

- Cli-1, 2, 4, 5, 14, 15, 16, 21, 24, 29, 31, 33, 37, 38: class names collided with existing Defects4J tests, so the class and file were renamed with the prefix Claude (originals in Result/Cli/Cli-N/original_name/).
- Cli-14: the rename also changed a file path inside a string literal; it was restored to the original and re-measured.
- Cli-24: the test prints getClass().getName(), so renaming changed its output. It was re-measured with the original class name by temporarily moving the existing Defects4J test file out of the checkout and restoring it afterwards.
- All 39 bugs are counted in the Fault Detection Rate (none were excluded for environment reasons). Average coverage is over the 37 bugs whose test compiled.

## Method

- Fault detected = YES only if buggy fails and fixed passes (both compile).
- Coverage: defects4j coverage -s (Cobertura) on the fixed version, only classes modified by the bug fix.
- Script: claude_eval.sh. Logs: final_logs/. Round files: claude_final_results_*.csv.
