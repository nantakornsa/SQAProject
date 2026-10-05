# Claude Sonnet – Time (Defects4J) Summary

## Results

| Bug | Detected | Line cov | Cond cov |
|-----|----------|----------|----------|
| Time-1 | YES | 36/305 (11.8%) | 19/122 (15.6%) |
| Time-2 | YES | 70/301 (23.3%) | 23/118 (19.5%) |
| Time-3 | YES | 23/248 (9.3%) | 9/63 (14.3%) |
| Time-4 | YES | 62/253 (24.5%) | 30/106 (28.3%) |
| Time-5 | YES | 47/288 (16.3%) | 7/64 (10.9%) |
| Time-6 | YES | 9/380 (2.4%) | 2/182 (1.1%) |
| Time-7 | YES | 45/208 (21.6%) | 10/88 (11.4%) |
| Time-8 | YES | 99/357 (27.7%) | 43/182 (23.6%) |
| Time-9 | YES | 100/355 (28.2%) | 44/178 (24.7%) |
| Time-10 | YES | 15/71 (21.1%) | 8/46 (17.4%) |
| Time-11 | YES | 4/393 (1.0%) | 0/193 (0.0%) |
| Time-12 | YES | 64/676 (9.5%) | 16/244 (6.6%) |
| Time-13 | YES | 271/675 (40.1%) | 102/458 (22.3%) |
| Time-14 | YES | 14/118 (11.9%) | 4/52 (7.7%) |
| Time-15 | YES | 5/77 (6.5%) | 4/72 (5.6%) |
| Time-16 | YES | 48/208 (23.1%) | 12/88 (13.6%) |
| Time-17 | YES | 66/353 (18.7%) | 27/170 (15.9%) |
| Time-18 | YES | 133/352 (37.8%) | 23/158 (14.6%) |
| Time-19 | YES | 56/343 (16.3%) | 19/162 (11.7%) |
| Time-20 | YES | 173/1037 (16.7%) | 68/595 (11.4%) |
| Time-22 | YES | 18/176 (10.2%) | 0/58 (0.0%) |
| Time-26 | YES | 140/573 (24.4%) | 37/242 (15.3%) |

## Summary

- Bugs measured: 22 of 26 Time bugs (1-20, 22-27 in Defects4J 2.0)
- Fault detected: 22/22 = 100%
- Average line coverage: 18.3% (pooled 19.3%)
- Average condition coverage: 13.2% (pooled 13.9%)
- Coverage = classes modified by the bug fix only

## Not detected

None among the 22 measured bugs.

## Not measured

- Time-21: deprecated bug in Defects4J
- Time-23, 24, 25: PromptGenerator reported "Source diff is empty", so no prompt/test was generated (excluded: duplicate GWT copies of the same classes exist under JodaTimeContrib, and after excluding that folder the generated diff was a whole-file deletion rather than the bug fix; root cause of the second issue not determined. Prompts moved to Prompt_invalid_skipped/Time)
- Time-27: not attempted (API quota)

## Notes

- Time-6: generated test class TestGJDate collided with an existing test; renamed to ClaudeTestGJDate (file, class, and new TestSuite(...class) references). Original kept in Result/Time/Time-6/original_name/
- Time-19 and Time-26 both generated TestDateTimeZoneCutoverRegression; they live in separate bug folders so they do not overwrite each other
- Time-11 (ZoneInfoCompiler) has very low coverage (line 1.0%, cond 0.0%): the class is large and the regression test targets a single behavior; buggy FAIL / fixed PASS was confirmed
- Low coverage overall is expected: each test is a regression test aimed at one bug
- Measured in two batches (Time 1-10, Time 11-26), merged into claude_final_results_merged.csv (NO_TEST rows removed)
- Do not compare FDR across projects without stating the number of bugs measured

## Method

- Prompt: PromptGenerator (diff of modified sources + triggering test + bug info), same prompt for all projects
- Model: claude-sonnet-5.5 via KKU IntelSphere API, max_tokens 4096
- Fault detected = test fails on buggy and passes on fixed (both compile)
- Coverage: defects4j coverage (line and condition), script claude_eval.sh
