
# EvidenceGuard

## Digital Evidence Integrity & Chain-of-Custody Manager

EvidenceGuard is a Java command-line application developed as an educational project to manage digital evidence records, verify file integrity using SHA-256 hashing, and maintain a hash-linked chain of custody events.

## Features

- Register digital evidence with a unique evidence ID.
- Calculate and store SHA-256 file hashes.
- View registered evidence records.
- Verify whether evidence files have changed.
- Record custody events with handler, timestamp, and action.
- View custody history.
- Verify the consistency of the custody log's hash chain.
- Store records locally so they persist between program runs.

## Technologies Used

- Java 21
- Java Standard Library
- SHA-256 hashing
- File-based local storage
- Command-line interface
- Base64 encoding for text fields

## Project Structure

```text
EvidenceGuard/
├── src/
│   ├── AuditLog.java
│   ├── CustodyEvent.java
│   ├── Evidence.java
│   ├── EvidenceManager.java
│   ├── HashUtils.java
│   ├── Main.java
│   └── StorageManager.java
├── data/
│   ├── evidence.txt
│   └── custody.txt
├── out/
├── sample-evidence.txt
├── README.md
├── statement.md
└── EvidenceGuard_Report.docx
```

## Requirements

- Java Development Kit (JDK) 21 or a compatible version
- Command Prompt or terminal

Check your Java installation:

```bash
java -version
javac -version
```

## How to Compile

Open a terminal in the EvidenceGuard project root directory.

For Windows Command Prompt, run:

```bat
javac -d out src\*.java
```

## How to Run

After compiling, run:

```bash
java -cp out Main
```

## Main Menu

The application provides the following options:

0. Exit
1. Register Evidence
2. View All Evidence
3. Verify Evidence Integrity
4. Record Custody Event
5. View Custody History
6. Verify Custody Log

## Integrity Verification

When evidence is registered, EvidenceGuard calculates its SHA-256 hash and stores it with the evidence record.

During verification, the application recalculates the file's hash and compares it with the registered hash. A matching hash indicates that the file contents match the registered version. A mismatch indicates that the contents have changed.

## Chain of Custody

Custody events contain an evidence ID, timestamp, handler, action, previous-event hash, and event hash.

The application links custody events using hashes and provides a function to check the consistency of the stored hash chain.

## Storage

EvidenceGuard stores its records locally in the `data` directory:

- `evidence.txt` — stores registered evidence records.
- `custody.txt` — stores custody-event records.

Text fields are Base64-encoded to support delimiter-safe storage. Base64 is an encoding method, not encryption.

## Testing

The application was manually tested using a sample evidence file.

- Registered a sample evidence file and confirmed that an evidence ID was generated.
- Verified that an unchanged file produced an integrity match.
- Modified the sample file and confirmed that an integrity mismatch was detected.
- Recorded and viewed a custody event.
- Verified that the custody hash chain was consistent.
- Restarted the application and confirmed that saved records remained available.

## Limitations

- This is a local command-line prototype, not a certified forensic evidence platform.
- The custody log is stored locally and is not protected against a privileged user who can modify the files.
- Hash-chain consistency does not independently prove that recorded events are authentic.
- The current design uses one hash chain for custody events rather than separate chains for each evidence item.
- Evidence files are referenced by path; the application does not copy them into secure storage.
- The application does not provide user authentication, digital signatures, or protected key management.

## Future Enhancements

- Add user authentication and role-based access.
- Add digitally signed custody events.
- Use a tamper-resistant database or secure remote storage.
- Add evidence export and report generation.
- Support multiple independent custody chains.
- Add automated tests and a graphical user interface.

## Author

**N. Shirisha**  
B.Tech — Cybersecurity and Digital Forensics  
VIT Bhopal University

## Academic Project

Java Course Project — EvidenceGuard
