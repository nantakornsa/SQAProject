#!/usr/bin/env bash
# Summarize existing JaCoCo line coverage reports. Does not rerun experiments.
# Usage: ./summarize_line_coverage.sh [Result_Round1]
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RESULT_DIR="${1:-Result_Round1}"
[[ "$RESULT_DIR" = /* ]] || RESULT_DIR="$SCRIPT_DIR/$RESULT_DIR"

python3 - "$RESULT_DIR" <<'PY'
import csv, os, sys
from collections import defaultdict

root = os.path.abspath(sys.argv[1])
summary_path = os.path.join(root, "summary.csv")
if not os.path.isfile(summary_path):
    sys.exit(f"ไม่พบไฟล์: {summary_path}")

targets = {
    "mockito":"org.mockito.internal.invocation.InvocationMatcher",
    "cli":"org.apache.commons.cli.CommandLine",
    "codec":"org.apache.commons.codec.binary.Base64",
    "collections":"org.apache.commons.collections4.ListUtils",
    "csv":"org.apache.commons.csv.CSVFormat",
    "gson":"com.google.gson.Gson",
    "jsoup":"org.jsoup.nodes.Document",
    "lang":"org.apache.commons.lang3.StringUtils",
    "math":"org.apache.commons.math3.util.Precision",
    "closure":"com.google.javascript.jscomp.Compiler",
    "lang_f":"org.apache.commons.lang3.StringUtils",
    "time":"org.joda.time.DateTime",
    "compress":"org.apache.commons.compress.archivers.ArchiveStreamFactory",
    "jacksoncore":"com.fasterxml.jackson.core.JsonFactory",
    "jacksondatabind":"com.fasterxml.jackson.databind.ObjectMapper",
    "jacksonxml":"com.fasterxml.jackson.dataformat.xml.XmlMapper",
    "jxpath":"org.apache.commons.jxpath.JXPathContext",
}

with open(summary_path, newline="", encoding="utf-8-sig") as f:
    runs = list(csv.DictReader(f))
details = []
for run in runs:
    project, bug, status = run["project"].strip().lower(), run["bug"].strip(), run["status"].strip()
    target = targets.get(project, "")
    report = os.path.join(root, project, f"bug_{bug}", "coverage_report", "jacoco.csv")
    covered = missed = 0
    found = False
    if target and os.path.isfile(report):
        with open(report, newline="", encoding="utf-8-sig") as f:
            for row in csv.DictReader(f):
                fqcn = (row.get("PACKAGE", "") + "." + row.get("CLASS", "")).strip(".")
                if fqcn == target or fqcn.startswith(target + "$"):
                    covered += int(row.get("LINE_COVERED") or 0)
                    missed += int(row.get("LINE_MISSED") or 0)
                    found = True
    total = covered + missed
    pct = 100 * covered / total if total else None
    details.append({"project":project,"bug":bug,"status":status,"target_class":target,
                    "line_covered":covered if found else "","line_missed":missed if found else "",
                    "line_total":total if found else "","line_coverage_pct":f"{pct:.2f}" if found and total else "",
                    "report_found":"YES" if found else "NO"})

detail_path = os.path.join(root, "line_coverage_detail.csv")
detail_fields = ["project","bug","status","target_class","line_covered","line_missed","line_total","line_coverage_pct","report_found"]
with open(detail_path,"w",newline="",encoding="utf-8-sig") as f:
    w=csv.DictWriter(f,fieldnames=detail_fields); w.writeheader(); w.writerows(details)

groups=defaultdict(list)
for row in details: groups[row["project"]].append(row)
summary_out=os.path.join(root,"line_coverage_summary.csv")
fields=["project","runs","pass_runs","reports_found","avg_line_coverage_pass_pct","avg_line_coverage_with_report_pct","weighted_line_coverage_pass_pct","weighted_line_coverage_with_report_pct"]
with open(summary_out,"w",newline="",encoding="utf-8-sig") as f:
    w=csv.DictWriter(f,fieldnames=fields); w.writeheader()
    for project,rows in sorted(groups.items()):
        available=[r for r in rows if r["report_found"]=="YES" and r["line_total"]]
        passing=[r for r in available if r["status"]=="PASS"]
        def mean_pct(group): return f"{sum(float(r['line_coverage_pct']) for r in group)/len(group):.2f}" if group else ""
        def weighted_pct(group):
            total=sum(int(r["line_total"]) for r in group)
            return f"{100*sum(int(r['line_covered']) for r in group)/total:.2f}" if total else ""
        w.writerow({"project":project,"runs":len(rows),"pass_runs":sum(r["status"]=="PASS" for r in rows),
                    "reports_found":len(available),"avg_line_coverage_pass_pct":mean_pct(passing),
                    "avg_line_coverage_with_report_pct":mean_pct(available),
                    "weighted_line_coverage_pass_pct":weighted_pct(passing),
                    "weighted_line_coverage_with_report_pct":weighted_pct(available)})
print(f"สรุป: {summary_out}")
print(f"ราย bug: {detail_path}")
PY
