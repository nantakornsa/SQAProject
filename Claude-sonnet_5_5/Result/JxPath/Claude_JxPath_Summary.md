# Claude Sonnet 5.5 – Defects4J JxPath (22 bugs)

Model: claude-sonnet-5.5 (KKU IntelSphere API) | Benchmark: Defects4J JxPath 1–22

## Results

| Bug | Buggy (compile/test) | Fixed (compile/test) | Fault Detected | Line Coverage | Condition Coverage | Status |
|---|---|---|---|---|---|---|
| JxPath-1 | PASS/PASS | PASS/PASS | NO | 51/773 (6.6%) | 20/562 (3.6%) | MEASURED |
| JxPath-2 | PASS/FAIL | PASS/FAIL | NO | 31/43 (72.1%) | 8/16 (50.0%) | MEASURED |
| JxPath-3 | PASS/FAIL | PASS/PASS | YES | 16/83 (19.3%) | 4/34 (11.8%) | MEASURED |
| JxPath-4 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-5 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-6 | PASS/FAIL | PASS/PASS | YES | 29/53 (54.7%) | 22/66 (33.3%) | MEASURED |
| JxPath-7 | PASS/FAIL | PASS/PASS | YES | 36/56 (64.3%) | 24/40 (60.0%) | MEASURED |
| JxPath-8 | PASS/FAIL | PASS/PASS | YES | 33/43 (76.7%) | 24/36 (66.7%) | MEASURED |
| JxPath-9 | PASS/FAIL | PASS/PASS | YES | 44/68 (64.7%) | 27/64 (42.2%) | MEASURED |
| JxPath-10 | PASS/FAIL | PASS/PASS | YES | 21/43 (48.8%) | 17/36 (47.2%) | MEASURED |
| JxPath-11 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-12 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-13 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-14 | PASS/FAIL | PASS/PASS | YES | 28/361 (7.8%) | 15/224 (6.7%) | MEASURED |
| JxPath-15 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-16 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-17 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-18 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-19 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |
| JxPath-20 | PASS/FAIL | PASS/PASS | YES | 25/49 (51.0%) | 16/40 (40.0%) | MEASURED |
| JxPath-21 | PASS/FAIL | PASS/PASS | YES | 19/71 (26.8%) | 5/54 (9.3%) | MEASURED |
| JxPath-22 | n/a | n/a | N/A | n/a | n/a | ENV_ERROR |

## Summary

- Bugs total: 22 | measured: 11 | not measurable (environment): 11
- Fault Detection Rate (measured bugs only): 9/11 = 81.82%
- Average Line Coverage (measured bugs): 44.8%
- Average Condition Coverage (measured bugs): 33.7%

## Not detected (measured)

- JxPath-1: the Claude test passes on both buggy and fixed versions.
- JxPath-2: the Claude test fails on the fixed version (expected /beans[1] but was /.[@name='beans'][1]).

## Not measurable (environment)

- JxPath-4, 5, 11, 12, 13, 15, 16, 17, 18, 19, 22: tests fail on both versions with NoClassDefFoundError: org/w3c/dom/ls/DocumentLS (class removed in Java 9+; Defects4J here runs on Java 11). These are excluded from the Fault Detection Rate and have no coverage value. They are not counted as Claude failures.

## Notes

- JxPath-3, 4, 19, 20, 21, 22: class names collided with existing Defects4J tests, so the class and file were renamed with the prefix Claude. Test content is unchanged (originals in Result/JxPath/JxPath-N/original_name/).
- JxPath-20 extends JXPathTestCase (abstract, no test methods), so that file was added to the -s archive. Its failure 'No tests found in JXPathTestCase' is an artifact of the method and is ignored.

## Method

- Fault detected = YES only if buggy fails and fixed passes (both compile).
- Coverage: defects4j coverage -s (Cobertura) on the fixed version, only classes modified by the bug fix.
- Script: claude_eval.sh. Logs: final_logs/. Round files: claude_final_results_round*.csv.
- JxPath-20 values (buggy FAIL, fixed PASS, 25/49 lines, 16/40 conditions) come from a separate run with JXPathTestCase.java added to the archive, not from the round CSV files.
