# Group 13

## Member

| No. | Student ID | Name |
|:---:|:---:|---|
| 1 | 673380085-0 | ชื่อ นายนันทกร แสวงจิตร |
| 2 | 673380414-7 | ชื่อ นายพชรพงศ์ สาหล่อน |
| 3 | 673380604-2 | ชื่อ นายศุภวัทน์ แสนเรียน |

# ProjectName

โปรเจครายวิชา SQA: เปรียบเทียบผลลัพธ์ของ 2 อัลกอริทึม (AlgorithmName1, AlgorithmName2)
พร้อมโมดูล Claude-sonnet_4_6 สำหรับใช้ Claude ช่วยสร้าง/รัน regression test อัตโนมัติ

## โครงสร้างโปรเจค

```
ProjectName/
├── pom.xml                    <- parent pom (aggregator)
├── AlgorithmName1/
│   ├── pom.xml
│   ├── src/main/java/...      <- โค้ดอัลกอริทึม 1
│   ├── src/test/java/...      <- unit test
│   ├── src/main/resources/    <- ไฟล์ config
│   ├── Result_Round1/
│   └── Result_Round2/
├── AlgorithmName2/
│   ├── pom.xml
│   ├── src/main/java/...      <- โค้ดอัลกอริทึม 2
│   ├── src/test/java/...
│   ├── src/main/resources/
│   ├── Result_Round1/
│   └── Result_Round2/
├── Claude-sonnet_4_6/
│   ├── pom.xml
│   ├── src/main/java/...      <- ClaudeClient.java เรียก Anthropic API
│   ├── Prompt/                <- prompt template ที่ใช้สั่ง Claude
│   ├── Result/                <- ผลลัพธ์ดิบจาก Claude API
│   └── TestCode/              <- test code ที่ Claude generate ให้ (เอาไปวางใน src/test ภายหลังได้)
├── pom.xml
└── lib
    ├── chart-buggy/             ← 26 bugs
    ├── cli-buggy/               ← 40 bugs
    ├── codec-buggy/             ← 18 bugs
    ├── collections-buggy/       ← 28 bugs
    ├── compress-buggy/          ← 47 bugs
    ├── csv-buggy/               ← 16 bugs
    ├── gson-buggy/              ← 18 bugs
    ├── jacksoncore-buggy/       ← 26 bugs
    ├── jacksondatabind-buggy/   ← 112 bugs
    ├── jacksonxml-buggy/        ← 6 bugs
    ├── jsoup-buggy/             ← 93 bugs
    ├── jxpath-buggy/            ← 22 bugs
    ├── math-buggy/              ← 106 bugs
    └── mockito-buggy/           ← 38 bugs
    
```

## วิธีใช้งาน

### 1. Build ทั้งโปรเจค
```bash
mvn clean install
```
คำสั่งนี้ build ทุก module ตามลำดับที่ประกาศใน `pom.xml` (root)

### 2. รัน test ของแต่ละ algorithm
```bash
cd AlgorithmName1
mvn test

cd ../AlgorithmName2
mvn test
```

### 3. ใช้ Claude generate test case อัตโนมัติ
ตั้งค่า API key ก่อน:
```bash
export ANTHROPIC_API_KEY=sk-ant-xxxxxxxx
```
แก้ prompt ที่ `Claude-sonnet_4_6/Prompt/generate_test_prompt.txt` ตามต้องการ แล้วรัน:
```bash
cd Claude-sonnet_4_6
mvn compile exec:java -Dexec.mainClass="com.example.claudesonnet.ClaudeClient"
```
ผลลัพธ์ (raw JSON response) จะถูกบันทึกไว้ที่ `Result/response.json`
จากนั้นนำ test code ที่ได้ไปใส่ในโฟลเดอร์ `TestCode/` หรือ copy ไปไว้ใน `src/test/java` ของ module ที่ต้องการ

## หมายเหตุ

- โฟลเดอร์ `Result_Round1`, `Result_Round2`, `Prompt`, `Result`, `TestCode` อยู่นอก `src/`
  ดังนั้น Maven จะไม่ compile หรือยุ่งกับไฟล์ในนี้ — ใช้เก็บข้อมูล/ผลลัพธ์ได้อิสระ
- ถ้าต้องการให้ทั้งสอง algorithm implement interface กลางร่วมกัน (เพื่อเทียบผลลัพธ์ง่ายขึ้น)
  แนะนำสร้าง module เพิ่ม เช่น `common/` แล้วให้ AlgorithmName1/2 depend on module นั้น
