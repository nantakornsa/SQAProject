# Claude Sonnet 5.5 – Defects4J Time (25 of 26 bugs measured)

Model: claude-sonnet-5.5 (KKU IntelSphere API) | Benchmark: Defects4J Time 1-20, 22-27 (bug 21 does not exist in this version)

## Results

| Bug | Buggy (compile/test) | Fixed (compile/test) | Fault Detected | Line Coverage | Condition Coverage |
|---|---|---|---|---|---|
| Time-1 | PASS/FAIL | PASS/PASS | YES | 36/305 (11.8%) | 19/122 (15.6%) |
| Time-2 | PASS/FAIL | PASS/PASS | YES | 70/301 (23.3%) | 23/118 (19.5%) |
| Time-3 | PASS/FAIL | PASS/PASS | YES | 23/248 (9.3%) | 9/63 (14.3%) |
| Time-4 | PASS/FAIL | PASS/PASS | YES | 62/253 (24.5%) | 30/106 (28.3%) |
| Time-5 | PASS/FAIL | PASS/PASS | YES | 47/288 (16.3%) | 7/64 (10.9%) |
| Time-6 | PASS/FAIL | PASS/PASS | YES | 9/380 (2.4%) | 2/182 (1.1%) |
| Time-7 | PASS/FAIL | PASS/PASS | YES | 45/208 (21.6%) | 10/88 (11.4%) |
| Time-8 | PASS/FAIL | PASS/PASS | YES | 99/357 (27.7%) | 43/182 (23.6%) |
| Time-9 | PASS/FAIL | PASS/PASS | YES | 100/355 (28.2%) | 44/178 (24.7%) |
| Time-10 | PASS/FAIL | PASS/PASS | YES | 15/71 (21.1%) | 8/46 (17.4%) |
| Time-11 | PASS/FAIL | PASS/PASS | YES | 4/393 (1.0%) | 0/193 (0.0%) |
| Time-12 | PASS/FAIL | PASS/PASS | YES | 64/676 (9.5%) | 16/244 (6.6%) |
| Time-13 | PASS/FAIL | PASS/PASS | YES | 271/675 (40.1%) | 102/458 (22.3%) |
| Time-14 | PASS/FAIL | PASS/PASS | YES | 14/118 (11.9%) | 4/52 (7.7%) |
| Time-15 | PASS/FAIL | PASS/PASS | YES | 5/77 (6.5%) | 4/72 (5.6%) |
| Time-16 | PASS/FAIL | PASS/PASS | YES | 48/208 (23.1%) | 12/88 (13.6%) |
| Time-17 | PASS/FAIL | PASS/PASS | YES | 66/353 (18.7%) | 27/170 (15.9%) |
| Time-18 | PASS/FAIL | PASS/PASS | YES | 133/352 (37.8%) | 23/158 (14.6%) |
| Time-19 | PASS/FAIL | PASS/PASS | YES | 56/343 (16.3%) | 19/162 (11.7%) |
| Time-20 | PASS/FAIL | PASS/PASS | YES | 173/1037 (16.7%) | 68/595 (11.4%) |
| Time-22 | PASS/FAIL | PASS/PASS | YES | 18/176 (10.2%) | 0/58 (0.0%) |
| Time-23 | PASS/FAIL | PASS/PASS | YES | 118/343 (34.4%) | 33/162 (20.4%) |
| Time-24 | PASS/FAIL | PASS/PASS | YES | 71/147 (48.3%) | 33/70 (47.1%) |
| Time-25 | PASS/FAIL | PASS/PASS | YES | 62/336 (18.5%) | 23/158 (14.6%) |
| Time-26 | PASS/FAIL | PASS/PASS | YES | 140/573 (24.4%) | 37/242 (15.3%) |
| Time-27 | not run | not run | not run | n/a | n/a |

## Summary

- Bugs measured: 25 of 26
- Fault Detection Rate (measured bugs only): 25/25 = 100.00%
- Average Line Coverage: 20.1% (over 25 bugs)
- Average Condition Coverage: 14.9% (over 25 bugs)

## Not yet measured

- Time-27: the prompt exists but the API request failed with HTTP 401 This model reached daily limit, so no test was generated yet. It is not counted as detected or not detected.

## Method

- Fault detected = YES only if buggy fails and fixed passes (both compile).
- Coverage: defects4j coverage -s (Cobertura) on the fixed version, only classes modified by the bug fix.
- Script: claude_eval.sh. Logs: final_logs/. Round files: claude_final_results_part*.csv. Previous 22-bug summary kept as Claude_Time_Summary_22bugs_backup.md.
