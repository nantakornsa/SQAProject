#!/usr/bin/env bash
# =========================================================================
# run_evosuite_defects4j_v2.sh
#
# วนลูปรัน EvoSuite (WTS) กับทุก bug ของทุก project ใน Defects4J โดยอัตโนมัติ
# สำหรับ Algorithm1 - Whole Test Suite Generation (WTS)
#
# เวอร์ชันนี้ใช้ EvoSuite ที่ bundled มากับ Defects4J โดยตรง
# (framework/lib/test_generation/generation/evosuite-current.jar)
# และประกอบ classpath แบบเดียวกับที่ Defects4J ใช้เอง (cp.compile)
# จึงไม่ต้องดาวน์โหลด EvoSuite แยกและลดปัญหาเรื่อง classpath ไม่ตรงกัน
#
# วิธีใช้:
#   1. แก้ CONFIG ด้านล่างให้ตรงกับเครื่องของคุณ
#   2. chmod +x run_evosuite_defects4j_v2.sh
#   3. ./run_evosuite_defects4j_v2.sh
#
# แนะนำ: รันผ่าน tmux เพราะใช้เวลานานมาก
#   tmux new -s evosuite_run
#   ./run_evosuite_defects4j_v2.sh
#   (Ctrl+B แล้ว D เพื่อออกจาก tmux โดยให้ script รันต่อ)
#   กลับมาดูด้วย: tmux attach -t evosuite_run
# =========================================================================

set -uo pipefail

# ========================= CONFIG (แก้ตรงนี้) ===========================

# path ที่ clone defects4j ไว้
D4J_HOME="$HOME/defects4j"

# jar ของ EvoSuite ที่ bundled มากับ Defects4J (ปกติไม่ต้องแก้ ถ้า D4J_HOME ถูกแล้ว)
EVOSUITE_JAR="$D4J_HOME/framework/lib/test_generation/generation/evosuite-current.jar"

# ที่เก็บ checkout ชั่วคราว (จะถูกลบทิ้งหลังรันแต่ละ bug เพื่อประหยัดพื้นที่)
WORK_ROOT="$HOME/sqa_project/algorithm1_wts/workdir"

# ที่เก็บผลลัพธ์ -- เปลี่ยนเป็น Result_Round2 ตอนรันรอบสอง
RESULT_ROOT="$HOME/sqa_project/algorithm1_wts/Result_Round2"

# งบเวลาค้นหารวมต่อ 1 bug (วินาที) -- ถ้า bug มีหลาย class จะถูกหารเฉลี่ยกัน
# (ตามสูตรเดียวกับที่ Defects4J ใช้เอง: budget ต่อ class = TOTAL_BUDGET / 2 / จำนวน class)
TOTAL_BUDGET=60

# random seed สำหรับ EvoSuite (เปลี่ยนค่านี้เพื่อรันซ้ำหลาย seed ตามข้อ 1.7 ของโจทย์)
SEED=1

# coverage criteria -- ค่า default ของ Defects4J คือ "branch" อย่างเดียว
# ปรับเป็นหลายตัวได้ตามที่โจทย์ต้องการวัดหลายมิติ (เช่น mutation score ด้วย)
CRITERION="branch:line:exception:weakmutation"

# รายชื่อ project ที่จะรัน
PROJECTS=(Lang Math Chart Time Cli Codec Collections Compress Csv Gson JacksonCore JacksonDatabind JacksonXml Jsoup JxPath Mockito Closure)

# จำกัดจำนวน bug ต่อ project เพื่อทดสอบ pipeline ก่อน (0 = ไม่จำกัด, รันครบทุก bug)
# แนะนำ: ตั้งเป็น 2-3 ก่อนครั้งแรกเพื่อเช็คว่า script ทำงานถูกต้อง
MAX_BUGS_PER_PROJECT=0

# ==========================================================================

# เช็คว่า evosuite jar มีอยู่จริงก่อนเริ่ม
if [[ ! -f "$EVOSUITE_JAR" ]]; then
    echo "ไม่พบไฟล์ EvoSuite ที่: $EVOSUITE_JAR"
    echo "เช็คด้วย: find \$HOME -iname 'evosuite-current.jar'"
    exit 1
fi

mkdir -p "$WORK_ROOT" "$RESULT_ROOT"
SUMMARY_CSV="$RESULT_ROOT/summary.csv"

if [[ ! -f "$SUMMARY_CSV" ]]; then
    echo "project,bug_id,class,status,duration_sec" > "$SUMMARY_CSV"
fi

TOTAL_START=$(date +%s)

for PID in "${PROJECTS[@]}"; do
    echo "=============================================="
    echo " Project: $PID"
    echo "=============================================="

    mapfile -t BUG_IDS < <(defects4j bids -p "$PID")
    echo "พบ ${#BUG_IDS[@]} bugs ใน $PID"

    COUNT=0
    for BID in "${BUG_IDS[@]}"; do
        COUNT=$((COUNT + 1))
        if [[ "$MAX_BUGS_PER_PROJECT" -gt 0 && "$COUNT" -gt "$MAX_BUGS_PER_PROJECT" ]]; then
            echo "ถึงลิมิต MAX_BUGS_PER_PROJECT=$MAX_BUGS_PER_PROJECT แล้ว ข้ามที่เหลือของ $PID"
            break
        fi

        # ข้ามถ้าเคยรันสำเร็จแล้ว (รัน script ซ้ำได้โดยไม่ทำงานซ้ำ)
        if grep -q "^${PID},${BID}," "$SUMMARY_CSV" 2>/dev/null; then
            echo "ข้าม $PID-$BID (มีผลอยู่แล้วใน summary.csv)"
            continue
        fi

        WDIR="$WORK_ROOT/${PID}_${BID}b"
        rm -rf "$WDIR"

        echo "---- [$PID-$BID] Checkout (buggy version) ----"
        defects4j checkout -p "$PID" -v "${BID}b" -w "$WDIR"
        if [[ $? -ne 0 ]]; then
            echo "$PID,$BID,,checkout_failed,0" >> "$SUMMARY_CSV"
            continue
        fi

        echo "---- [$PID-$BID] Compile ----"
        defects4j compile -w "$WDIR"
        if [[ $? -ne 0 ]]; then
            echo "$PID,$BID,,compile_failed,0" >> "$SUMMARY_CSV"
            rm -rf "$WDIR"
            continue
        fi

        # คลาสที่ bug นี้แก้ไข (เป้าหมายหลักในการยิง EvoSuite ต่อ bug)
        MOD_CLASSES=$(defects4j export -p classes.modified -w "$WDIR" 2>/dev/null)
        # classpath แบบเดียวกับที่ Defects4J ใช้เอง (ดูจาก _tool.source: get_project_cp)
        PROJECT_CP=$(defects4j export -p cp.compile -w "$WDIR" 2>/dev/null)

        if [[ -z "$MOD_CLASSES" ]]; then
            echo "$PID,$BID,,no_modified_class,0" >> "$SUMMARY_CSV"
            rm -rf "$WDIR"
            continue
        fi

        NUM_CLASSES=$(echo "$MOD_CLASSES" | wc -l)
        BUDGET_PER_CLASS=$(( TOTAL_BUDGET / 2 / NUM_CLASSES ))
        [[ "$BUDGET_PER_CLASS" -lt 10 ]] && BUDGET_PER_CLASS=10

        for CLASS in $MOD_CLASSES; do
            echo "---- [$PID-$BID] EvoSuite -> $CLASS (budget ${BUDGET_PER_CLASS}s) ----"
            OUT_DIR="$RESULT_ROOT/${PID}_${BID}b/$(echo "$CLASS" | tr '.' '_')"
            mkdir -p "$OUT_DIR"

            START=$(date +%s)
            java -cp "$EVOSUITE_JAR" org.evosuite.EvoSuite \
                -class "$CLASS" \
                -projectCP "$PROJECT_CP" \
                -seed "$SEED" \
                -criterion "$CRITERION" \
                -Dstopping_condition=MaxTime \
                -Dsearch_budget="$BUDGET_PER_CLASS" \
                -Dassertion_timeout="$BUDGET_PER_CLASS" \
                -Dtest_dir="$OUT_DIR" \
                -Dreport_dir="$OUT_DIR" \
                -Dshow_progress=false \
                -Djunit_check=false \
                -Dtest_comments=false \
                -mem 1500 \
                > "$OUT_DIR/evosuite_log.txt" 2>&1
            STATUS=$?
            END=$(date +%s)
            DURATION=$((END - START))

            if [[ $STATUS -eq 0 ]]; then
                echo "$PID,$BID,$CLASS,ok,$DURATION" >> "$SUMMARY_CSV"
                echo "   เสร็จใน ${DURATION}s -- ผลลัพธ์อยู่ที่ $OUT_DIR"
            else
                echo "$PID,$BID,$CLASS,evosuite_failed,$DURATION" >> "$SUMMARY_CSV"
                echo "   EvoSuite FAILED ดู log ที่ $OUT_DIR/evosuite_log.txt"
            fi
        done

        rm -rf "$WDIR"   # ลบ checkout ทิ้งทันทีเพื่อประหยัดดิสก์
    done
done

TOTAL_END=$(date +%s)
TOTAL_DURATION=$(( (TOTAL_END - TOTAL_START) / 60 ))

echo ""
echo "=============================================="
echo " เสร็จสิ้นทั้งหมด ใช้เวลารวม ${TOTAL_DURATION} นาที"
echo " สรุปผลอยู่ที่: $SUMMARY_CSV"
echo "=============================================="
