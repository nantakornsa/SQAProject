# Claude Sonnet 5.5 – Defects4J Gson (18 bugs)

Model: claude-sonnet-5.5 (KKU IntelSphere API) | Benchmark: Defects4J Gson 1–18

## Results

| Bug | Buggy (compile/test) | Fixed (compile/test) | Fault Detected | Line Coverage | Condition Coverage |
|---|---|---|---|---|---|
| Gson-1 | PASS/FAIL | PASS/PASS | YES | 53/79 (67.1%) | 26/44 (59.1%) |
| Gson-2 | PASS/FAIL | PASS/PASS | YES | 108/454 (23.8%) | 9/192 (4.7%) |
| Gson-3 | PASS/FAIL | PASS/PASS | YES | 33/94 (35.1%) | 11/36 (30.6%) |
| Gson-4 | PASS/FAIL | PASS/PASS | YES | 263/876 (30.0%) | 114/557 (20.5%) |
| Gson-5 | PASS/FAIL | PASS/PASS | YES | 70/151 (46.4%) | 33/91 (36.3%) |
| Gson-6 | PASS/FAIL | PASS/PASS | YES | 15/21 (71.4%) | 4/8 (50.0%) |
| Gson-7 | PASS/FAIL | PASS/FAIL | NO | 198/688 (28.8%) | 102/477 (21.4%) |
| Gson-8 | PASS/FAIL | PASS/PASS | YES | 16/40 (40.0%) | 4/4 (100.0%) |
| Gson-9 | PASS/FAIL | PASS/PASS | YES | 231/784 (29.5%) | 38/338 (11.2%) |
| Gson-10 | PASS/FAIL | PASS/PASS | YES | 92/117 (78.6%) | 31/60 (51.7%) |
| Gson-11 | PASS/FAIL | PASS/PASS | YES | 117/500 (23.4%) | 6/196 (3.1%) |
| Gson-12 | PASS/FAIL | PASS/PASS | YES | 28/170 (16.5%) | 8/90 (8.9%) |
| Gson-13 | PASS/FAIL | PASS/PASS | YES | 142/683 (20.8%) | 77/488 (15.8%) |
| Gson-14 | PASS/FAIL | PASS/PASS | YES | 42/247 (17.0%) | 25/194 (12.9%) |
| Gson-15 | PASS/FAIL | PASS/PASS | YES | 70/198 (35.4%) | 14/94 (14.9%) |
| Gson-16 | PASS/FAIL | PASS/PASS | YES | 132/251 (52.6%) | 75/196 (38.3%) |
| Gson-17 | PASS/FAIL | PASS/PASS | YES | 15/56 (26.8%) | 3/16 (18.8%) |
| Gson-18 | PASS/FAIL | PASS/PASS | YES | 129/253 (51.0%) | 71/198 (35.9%) |

## Summary

- Fault Detection Rate: 17/18 = 94.44%
- Average Line Coverage: 38.6% (over 18 bugs whose test compiled)
- Average Condition Coverage: 29.7% (over 18 bugs whose test compiled)

## Not detected

- Gson-7: the Claude test fails on both buggy and fixed versions (testReaderNextIntOnUnquotedName throws IllegalStateException: Expected an int but was NAME on the fixed version).
- Generated tests were not modified.

## Notes

- Gson-4, 8, 9, 10, 13, 15, 18: the first extraction failed because the code extractor did not recognise `public final class` (and, for Gson-18, picked a snippet block instead of the test file). The extractor was fixed and the tests were re-extracted from the saved response.txt. No new API call was made and the test content was not changed.
- Gson-8 condition coverage is 100% (4/4) because the measured class has only 4 conditions.

## Method

- Fault detected = YES only if buggy fails and fixed passes (both compile).
- Coverage: defects4j coverage -s (Cobertura) on the fixed version, only classes modified by the bug fix, only Claude-generated tests.
- Script: claude_eval.sh. Logs: final_logs/.
