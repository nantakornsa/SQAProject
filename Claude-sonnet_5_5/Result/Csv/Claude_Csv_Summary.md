# Claude Sonnet 5.5 – Defects4J Csv (16 bugs)

Model: claude-sonnet-5.5 (KKU IntelSphere API) | Benchmark: Defects4J Csv 1–16

## Results

| Bug | Buggy (compile/test) | Fixed (compile/test) | Fault Detected | Line Coverage | Condition Coverage |
|---|---|---|---|---|---|
| Csv-1 | PASS/FAIL | PASS/PASS | YES | 15/37 (40.5%) | 3/26 (11.5%) |
| Csv-2 | FAIL/NOT_RUN | FAIL/NOT_RUN | NO | n/a (test did not compile) | n/a |
| Csv-3 | FAIL/NOT_RUN | FAIL/NOT_RUN | NO | n/a (test did not compile) | n/a |
| Csv-4 | PASS/FAIL | PASS/PASS | YES | 3/100 (3.0%) | 0/52 (0.0%) |
| Csv-5 | PASS/FAIL | PASS/PASS | YES | 29/159 (18.2%) | 9/117 (7.7%) |
| Csv-6 | PASS/FAIL | PASS/PASS | YES | 14/36 (38.9%) | 5/20 (25.0%) |
| Csv-7 | PASS/FAIL | PASS/PASS | YES | 49/103 (47.6%) | 14/52 (26.9%) |
| Csv-8 | PASS/FAIL | PASS/PASS | YES | 54/178 (30.3%) | 25/140 (17.9%) |
| Csv-9 | PASS/FAIL | PASS/PASS | YES | 10/38 (26.3%) | 2/24 (8.3%) |
| Csv-10 | PASS/FAIL | PASS/PASS | YES | 60/162 (37.0%) | 31/119 (26.1%) |
| Csv-11 | PASS/FAIL | PASS/FAIL | NO | 53/106 (50.0%) | 25/62 (40.3%) |
| Csv-12 | PASS/FAIL | PASS/PASS | YES | 63/184 (34.2%) | 29/140 (20.7%) |
| Csv-13 | PASS/FAIL | PASS/PASS | YES | 131/387 (33.9%) | 58/291 (19.9%) |
| Csv-14 | PASS/FAIL | PASS/PASS | YES | 103/356 (28.9%) | 61/272 (22.4%) |
| Csv-15 | PASS/FAIL | PASS/PASS | YES | 144/395 (36.5%) | 57/261 (21.8%) |
| Csv-16 | PASS/FAIL | PASS/PASS | YES | 57/124 (46.0%) | 20/68 (29.4%) |

## Summary

- Fault Detection Rate: 13/16 = 81.25%
- Average Line Coverage: 33.7% (over 14 bugs whose test compiled)
- Average Condition Coverage: 19.8% (over 14 bugs whose test compiled)

## Not detected

- Csv-2: the Claude test does not compile (CSVFormat.withHeader(String...) does not exist in this version).
- Csv-3: the Claude test does not compile (CSVFormat.withEscape(char) does not exist in this version).
- Csv-11: the Claude test fails on both buggy and fixed versions (assertFalse at line 26 fails on the fixed version).
- Generated tests were not modified. Csv-2 and Csv-3 have no coverage value (not 0%).

## Method

- Fault detected = YES only if buggy fails and fixed passes (both compile).
- Coverage: defects4j coverage -s (Cobertura) on the fixed version, only classes modified by the bug fix, only Claude-generated tests.
- Script: claude_eval.sh. Logs: final_logs/.
