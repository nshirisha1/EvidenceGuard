\# EvidenceGuard



\## Digital Evidence Integrity \& Chain-of-Custody Manager



EvidenceGuard is a Java command-line application designed to help manage digital evidence records, verify file integrity using SHA-256 hashing, and maintain a hash-linked chain of custody events.



\## Features



\* Register digital evidence with a unique evidence ID.

\* Calculate and store SHA-256 file hashes.

\* View registered evidence records.

\* Verify whether evidence files have changed.

\* Record custody events with handler, timestamp, and action.

\* View custody history for an evidence item.

\* Verify the consistency of the custody log's hash chain.

\* Store records locally so they persist between program runs.



\## Technologies Used



\* Java 21

\* Java Standard Library

\* SHA-256 hashing

\* File-based storage

\* Command-line interface



\## Project Structure



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

├── out/

├── sample-evidence.txt

├── README.md

└── statement.md

```



\## Requirements



\* Java Development Kit (JDK) 21 or compatible version

\* Windows, macOS, or Linux terminal



\## How to Compile



From the project root, run:



```bash

javac -d out src/\*.java

```



On Windows Command Prompt, this command also works:



```cmd

javac -d out src\\\*.java

```



\## How to Run



```bash

java -cp out Main

```



\## Main Menu



1\. Register Evidence

2\. View All Evidence

3\. Verify Evidence Integrity

4\. Record Custody Event

5\. View Custody History

6\. Verify Custody Log

7\. Exit



\## Integrity Verification



When evidence is registered, EvidenceGuard calculates its SHA-256 hash. During verification, it recalculates the hash and compares it with the stored value. A mismatch indicates that the file contents have changed since registration.



\## Chain of Custody



Custody events include an evidence ID, timestamp, handler, action, previous hash, and event hash. The application links events using hashes and provides a function to check the consistency of the stored chain.



\## Limitations



\* This is a local command-line prototype, not a certified forensic evidence platform.

\* The custody log is stored locally and is not protected against a privileged user who can modify the files.

\* Hash-chain consistency does not independently prove that recorded events are authentic.

\* The current design uses one hash chain for custody events rather than separate chains for each evidence item.

\* Evidence files are referenced by path; the application does not copy them into secure storage.



\## Future Enhancements



\* Add user authentication and role-based access.

\* Add digitally signed custody events.

\* Use a tamper-resistant database or secure remote storage.

\* Add evidence export and report generation.

\* Support multiple independent custody chains.



\## Author



N. Shirisha



\## Academic Project



Java Course Project — EvidenceGuard



