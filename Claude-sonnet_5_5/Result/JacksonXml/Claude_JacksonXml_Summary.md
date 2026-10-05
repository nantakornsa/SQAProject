# Claude Sonnet 5.5 - Defects4J JacksonXml (6 bugs)

## Results

| Bug | Buggy (compile/test) | Fixed (compile/test) | Fault Detected | Line Coverage | Condition Coverage |
|---|---|---|---|---|---|
| JacksonXml-1 | PASS/PASS | PASS/PASS | NO | 86/297 (29.0%) | 31/149 (20.8%) |
| JacksonXml-2 | PASS/FAIL | PASS/PASS | YES | 65/187 (34.8%) | 31/90 (34.4%) |
| JacksonXml-3 | PASS/FAIL | PASS/PASS | YES | 54/294 (18.4%) | 19/151 (12.6%) |
| JacksonXml-4 | PASS/FAIL | PASS/PASS | YES | 27/122 (22.1%) | 8/60 (13.3%) |
| JacksonXml-5 | PASS/FAIL | PASS/PASS | YES | 37/96 (38.5%) | 10/48 (20.8%) |
| JacksonXml-6 | PASS/FAIL | PASS/PASS | YES | 105/529 (19.8%) | 39/244 (16.0%) |

## Summary

- Fault Detection Rate: 5/6 = 83.33%
- Average Line Coverage: 27.1%
- Average Condition Coverage: 19.6%

## Not detected

- JacksonXml-1: the test passes on both the buggy and the fixed version.

## Notes

- JacksonXml-1, 3, 5, 6: class names collided with existing Defects4J tests, so the class and file were renamed with the prefix Claude (originals in Result/JacksonXml/JacksonXml-N/original_name/). No other content was changed.
- All generated tests extend XmlTestBase (a project helper in src/test). It was packed into the test archive so the tests compile. Defects4J also runs it as a test and it fails (no public constructor), so this failure is ignored when deciding PASS/FAIL. Script: claude_eval_jxml.sh (differs from claude_eval.sh only in these two points).
- Generated tests were not otherwise modified.
