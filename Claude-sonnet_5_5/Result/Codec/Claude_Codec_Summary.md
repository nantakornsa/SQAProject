# Claude Sonnet 5.5 – Defects4J Codec (18 bugs)

Model: claude-sonnet-5.5 (KKU IntelSphere API) | Benchmark: Defects4J Codec 1–18

## Results

| Bug | Buggy (compile/test) | Fixed (compile/test) | Fault Detected | Line Coverage | Condition Coverage |
|---|---|---|---|---|---|
| Codec-1 | PASS/FAIL | PASS/PASS | YES | 64/256 (25.0%) | 2/184 (1.1%) |
| Codec-2 | PASS/FAIL | PASS/PASS | YES | 45/214 (21.0%) | 25/148 (16.9%) |
| Codec-3 | PASS/FAIL | PASS/PASS | YES | 85/453 (18.8%) | 50/450 (11.1%) |
| Codec-4 | PASS/FAIL | PASS/PASS | YES | 75/231 (32.5%) | 35/158 (22.2%) |
| Codec-5 | PASS/FAIL | PASS/PASS | YES | 67/233 (28.8%) | 44/162 (27.2%) |
| Codec-6 | PASS/FAIL | PASS/PASS | YES | 22/38 (57.9%) | 15/30 (50.0%) |
| Codec-7 | PASS/FAIL | PASS/PASS | YES | 92/233 (39.5%) | 47/162 (29.0%) |
| Codec-8 | PASS/PASS | PASS/PASS | NO | 86/254 (33.9%) | 53/178 (29.8%) |
| Codec-9 | PASS/FAIL | PASS/PASS | YES | 89/220 (40.5%) | 42/154 (27.3%) |
| Codec-10 | PASS/FAIL | PASS/PASS | YES | 63/69 (91.3%) | 2/6 (33.3%) |
| Codec-11 | PASS/FAIL | PASS/PASS | YES | 67/107 (62.6%) | 37/70 (52.9%) |
| Codec-12 | PASS/FAIL | PASS/PASS | YES | 41/49 (83.7%) | 25/36 (69.4%) |
| Codec-13 | PASS/FAIL | PASS/PASS | YES | 19/506 (3.8%) | 5/482 (1.0%) |
| Codec-14 | PASS/PASS | PASS/FAIL | NO | 402/489 (82.2%) | 175/230 (76.1%) |
| Codec-15 | PASS/FAIL | PASS/PASS | YES | 34/51 (66.7%) | 24/32 (75.0%) |
| Codec-16 | PASS/FAIL | PASS/PASS | YES | 71/166 (42.8%) | 30/75 (40.0%) |
| Codec-17 | PASS/FAIL | PASS/PASS | YES | 7/39 (17.9%) | 1/20 (5.0%) |
| Codec-18 | PASS/FAIL | PASS/PASS | YES | 4/39 (10.3%) | 10/24 (41.7%) |

## Summary

- Fault Detection Rate: 16/18 = 88.89%
- Average Line Coverage: 42.2%
- Average Condition Coverage: 33.8%

## Not detected

- Codec-8: the Claude test passes on both buggy and fixed versions.
- Codec-14: the Claude test fails on the fixed version (expected value is missing `vntsn`). The generated test was not modified, so this is recorded as not detected. The buggy-version result differs between runs (earlier run: FAIL, final run: PASS); fault detection is NO in both cases.

## Method

- Fault detection: Fault Detected = YES only if buggy fails and fixed passes (compile OK on both).
- Coverage: `defects4j coverage` (Cobertura), measured on the fixed version, only for classes modified by the bug fix (default), using only the Claude-generated test.
- Codec-1 uses `-t Class::method` because its test class extends a Defects4J abstract test class; Codec-2 to 18 use `-s` (archive containing only the Claude test file).
- Denominators differ per bug (38–506 lines) because only the modified classes are measured.
- Raw logs: coverage_logs/, final_logs/. Scripts: scripts/.
