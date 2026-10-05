import os
import glob
import re
import pandas as pd
import openpyxl
from openpyxl.styles import Font, PatternFill, Alignment, Border, Side

base_dir = "/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject"

def parse_md_file(file_path, model, proj):
    with open(file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    bugs_data = []
    
    for line in lines:
        line_str = line.strip()
        if not line_str.startswith("|") or "---" in line_str:
            continue
            
        parts = [p.strip() for p in line_str.split("|")[1:-1]]
        if not parts:
            continue
            
        if parts[0].lower() in ["bug", "bug id", "no", "number", "#"] or "defect" in parts[0].lower():
            continue
            
        # Type B structure
        if len(parts) >= 6 and re.match(r'^(PASS|FAIL|NOT_RUN|not run)', parts[1], re.IGNORECASE):
            bug_id = parts[0]
            buggy = parts[1]
            fixed = parts[2]
            fd = parts[3].upper()
            
            m_line = re.search(r'([\d\.]+)%', parts[4])
            line_cov = float(m_line.group(1)) if m_line else None
            
            m_cond = re.search(r'([\d\.]+)%', parts[5])
            cond_cov = float(m_cond.group(1)) if m_cond else None
            
            note = parts[6] if len(parts) > 6 else ""
            status = "YES" if fd == "YES" else ("NO" if fd in ["NO", "FAIL"] else fd)
            
            bugs_data.append({
                "Model": model,
                "Project": proj,
                "Bug": bug_id,
                "Status": status,
                "Buggy (compile/test)": buggy,
                "Fixed (compile/test)": fixed,
                "Line Coverage %": line_cov,
                "Condition Coverage %": cond_cov,
                "Note": note
            })
            
        # Type A structure
        elif len(parts) >= 5:
            bug_id = parts[0]
            status_raw = parts[1].upper()
            
            if status_raw not in ["YES", "NO", "-"] and not bug_id.isdigit() and not re.search(r'-\d+', bug_id):
                continue
                
            status = status_raw
            
            try:
                line_cov = float(parts[2].replace('%', '')) if parts[2] not in ['-', 'n/a', ''] else None
            except:
                line_cov = None
                
            try:
                cond_cov = float(parts[3].replace('%', '')) if parts[3] not in ['-', 'n/a', ''] else None
            except:
                cond_cov = None
                
            note = parts[4] if len(parts) > 4 else ""
            
            bugs_data.append({
                "Model": model,
                "Project": proj,
                "Bug": bug_id,
                "Status": status,
                "Buggy (compile/test)": None,
                "Fixed (compile/test)": None,
                "Line Coverage %": line_cov,
                "Condition Coverage %": cond_cov,
                "Note": note
            })
            
    return bugs_data

# Model directories
claude_dir = os.path.join(base_dir, "Claude-sonnet_5_5")
gemini_dir = os.path.join(base_dir, "Gemini-3_8-flash")

models = {
    "Claude": os.path.join(claude_dir, "Result"),
    "Gemini": os.path.join(gemini_dir, "Result")
}

# Output file paths for both model folders
output_files = [
    os.path.join(claude_dir, "All_Summary.xlsx"),
    os.path.join(gemini_dir, "All_Summary.xlsx")
]

proj_data = {}

for model_name, path in models.items():
    if not os.path.exists(path):
        continue
    for proj in sorted(os.listdir(path)):
        proj_path = os.path.join(path, proj)
        if os.path.isdir(proj_path):
            md_files = [f for f in glob.glob(os.path.join(proj_path, "*Summary*.md")) 
                        if not any(x in f.lower() for x in ["backup", "old", "do_not_use", "_bak"])]
            if md_files:
                md_files.sort()
                target_file = md_files[0]
                bugs = parse_md_file(target_file, model_name, proj)
                key = f"{model_name}_{proj}"
                proj_data[key] = bugs

wb = openpyxl.Workbook()
wb.remove(wb.active)

# Styles
font_title = Font(name="Calibri", size=16, bold=True, color="1F497D")
font_subtitle = Font(name="Calibri", size=11, italic=True, color="595959")
font_section = Font(name="Calibri", size=13, bold=True, color="1F497D")
font_bold = Font(name="Calibri", size=11, bold=True)
font_regular = Font(name="Calibri", size=11)
font_header = Font(name="Calibri", size=11, bold=True, color="FFFFFF")
fill_header = PatternFill(start_color="1F497D", end_color="1F497D", fill_type="solid")
thin_border = Border(left=Side(style='thin', color='D9D9D9'),
                     right=Side(style='thin', color='D9D9D9'),
                     top=Side(style='thin', color='D9D9D9'),
                     bottom=Side(style='thin', color='D9D9D9'))

# ---------------------------------------------------------
# 1. Overview Sheet (หน้าแรก: รวมเฉพาะคำอธิบายภาพรวมโครงการ)
# ---------------------------------------------------------
ws_over = wb.create_sheet(title="Overview")
ws_over.views.sheetView[0].showGridLines = True

ws_over["A1"] = "รายงานภาพรวมโครงการวิจัย CP353201 Software Quality Assurance (SQA)"
ws_over["A1"].font = font_title

ws_over["A2"] = "การประเมินประสิทธิภาพของ LLMs ในการสร้าง Unit Test บน Defects4J Benchmark"
ws_over["A2"].font = font_subtitle

overview_lines = [
    ("วัตถุประสงค์ของโครงการ:", "ประเมินความสามารถของโมเดล AI ในการเจาะตรวจจับ Bug (Defects) แบบอัตโนมัติ โดยวัดค่า Fault Detection Rate (FDR), Line Coverage และ Condition Coverage"),
    ("เครื่องมือและโมเดลที่ใช้:", "1. Claude Sonnet 5.5 (ผ่าน KKU API Key)\n2. Gemini 3.8 Flash (ผ่าน KKU API Key)"),
    ("ชุดข้อมูลอ้างอิง (Benchmark):", "Defects4J (รวม 17 โปรเจกต์โอเพนซอร์สของภาษา Java) บนสภาพแวดล้อม Java 11"),
    ("การแบ่งความรับผิดชอบ:", "- Claude รับผิดชอบ 9 โปรเจกต์: Cli, Closure, Codec, Csv, Gson, JacksonXml, JxPath, Math, Time\n- Gemini รับผิดชอบ 8 โปรเจกต์: Chart, Collections, Compress, JacksonCore, JacksonDatabind, Jsoup, Lang, Mockito"),
    ("เกณฑ์การวัดผล (Metrics):", "- Fault Detection Rate (FDR) = (จำนวน Bug ที่ Detected / จำนวน Bug ที่วัดได้ทั้งหมด) * 100%\n- Line Coverage % = เปอร์เซ็นต์ความครอบคลุมของคำสั่งโค้ดที่ถูกทดสอบ\n- Condition Coverage % = เปอร์เซ็นต์ความครอบคลุมของเงื่อนไขทางตรรกะที่ถูกทดสอบ"),
    ("โครงสร้างชีตในไฟล์นี้:", "แต่ละชีตถัดไปคือสรุปผลการทดสอบแยกตามรายโปรเจกต์ ซึ่งจะประกอบด้วยบทวิเคราะห์สรุปผลเบื้องต้นก่อน แล้วตามด้วยตารางรายละเอียดของ Bug แต่ละตัว")
]

row_curr = 4
for head, content in overview_lines:
    ws_over[f"A{row_curr}"] = head
    ws_over[f"A{row_curr}"].font = font_section
    row_curr += 1
    for subline in content.split("\n"):
        ws_over[f"A{row_curr}"] = subline
        ws_over[f"A{row_curr}"].font = font_regular
        row_curr += 1
    row_curr += 1

# ---------------------------------------------------------
# 2. Individual Project Sheets (คำอธิบายรายโปรเจกต์ด้านบน + ตารางรายละเอียดด้านล่าง)
# ---------------------------------------------------------
for key, bugs in proj_data.items():
    model_name, proj = key.split("_", 1)
    sheet_title = key[:31]
    ws_p = wb.create_sheet(title=sheet_title)
    ws_p.views.sheetView[0].showGridLines = True
    
    ws_p["A1"] = f"รายงานผลการทดสอบโปรเจกต์: {proj}"
    ws_p["A1"].font = font_title
    
    # คำนวณค่าสถิติต่างๆ
    total = len(bugs)
    yes_cnt = sum(1 for b in bugs if b['Status'] == 'YES')
    no_cnt = sum(1 for b in bugs if b['Status'] == 'NO')
    other_cnt = total - yes_cnt - no_cnt
    fdr = (yes_cnt / (yes_cnt + no_cnt) * 100) if (yes_cnt + no_cnt) > 0 else 0
    
    line_covs = [b['Line Coverage %'] for b in bugs if b['Line Coverage %'] is not None]
    avg_line = (sum(line_covs) / len(line_covs)) if line_covs else 0.0
    
    cond_covs = [b['Condition Coverage %'] for b in bugs if b['Condition Coverage %'] is not None]
    avg_cond = (sum(cond_covs) / len(cond_covs)) if cond_covs else 0.0
    
    model_full_name = "Claude Sonnet 5.5 (KKU API Key)" if model_name == "Claude" else "Gemini 3.8 Flash (KKU API Key)"
    
    # ข้อความอธิบายเบื้องต้นก่อนตาราง
    intro_lines = [
        f"โมเดลที่ใช้ทดสอบ: {model_full_name}",
        f"โปรเจกต์ที่ทดสอบ: {proj} (รวมจำนวน Bug ทั้งหมด {total} ตัว)",
        f"ผลการตรวจจับ Bug (Fault Detection):",
        f"  - จำนวน Bug ที่ตรวจจับได้ (YES): {yes_cnt} ตัว",
        f"  - จำนวน Bug ที่ตรวจจับไม่ได้ (NO): {no_cnt} ตัว",
        f"  - วัดไม่ได้ / อื่นๆ (Unmeasurable / Other): {other_cnt} ตัว",
        f"  - อัตราการตรวจจับสำเร็จ (FDR): {fdr:.2f}% (คิดจากตัวที่วัดได้ {yes_cnt + no_cnt} ตัว)",
        f"ผลความครอบคลุมของการทดสอบ (Coverage Averages):",
        f"  - ค่าเฉลี่ย Line Coverage: {avg_line:.2f}%",
        f"  - ค่าเฉลี่ย Condition Coverage: {avg_cond:.2f}%"
    ]
    
    ws_p["A3"] = "บทอธิบายสรุปผลการทดสอบ:"
    ws_p["A3"].font = font_section
    
    r_idx = 4
    for line in intro_lines:
        ws_p[f"A{r_idx}"] = line
        if ":" in line and not line.startswith("  -"):
            ws_p[f"A{r_idx}"].font = font_bold
        else:
            ws_p[f"A{r_idx}"].font = font_regular
        r_idx += 1
        
    r_idx += 1
    ws_p[f"A{r_idx}"] = f"ตารางรายละเอียดผลการทดสอบราย Bug ของโปรเจกต์ {proj}:"
    ws_p[f"A{r_idx}"].font = font_section
    
    # วาดตารางด้านล่างข้อความอธิบาย
    table_start_row = r_idx + 2
    df_p = pd.DataFrame(bugs)
    headers_p = list(df_p.columns)
    
    for col_num, h_text in enumerate(headers_p, start=1):
        cell = ws_p.cell(row=table_start_row, column=col_num, value=h_text)
        cell.font = font_header
        cell.fill = fill_header
        cell.alignment = Alignment(horizontal="center", vertical="center")
        
    for r_offset, row_data in enumerate(df_p.values, start=1):
        for c_idx, val in enumerate(row_data, start=1):
            cell = ws_p.cell(row=table_start_row + r_offset, column=c_idx, value=val)
            cell.border = thin_border

# ปรับขนาดความกว้างคอลัมน์อัตโนมัติ
for sheet in wb.worksheets:
    for col in sheet.columns:
        max_len = 0
        col_letter = col[0].column_letter
        for cell in col:
            if cell.value and cell.row > 10:
                max_len = max(max_len, len(str(cell.value)))
        sheet.column_dimensions[col_letter].width = max(max_len + 4, 15)

# บันทึกไฟล์ Excel แยกไปยังโฟลเดอร์ของทั้ง 2 โมเดล
for out_path in output_files:
    os.makedirs(os.path.dirname(out_path), exist_ok=True)
    wb.save(out_path)
    print(f"Saved summary Excel to: {out_path}")

print("Updated summary_to_excel.py file successfully.")
