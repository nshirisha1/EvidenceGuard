\# Project Statement



\## Project Title



EvidenceGuard: Digital Evidence Integrity \& Chain-of-Custody Manager



\## Objective



To develop a Java command-line application that records digital evidence metadata, verifies evidence integrity using SHA-256 hashing, and maintains a hash-linked custody log.



\## Problem



Digital evidence files may be altered, and documenting who handled evidence and when is important. Manually maintained records can be difficult to verify consistently.



\## Proposed Solution



EvidenceGuard provides a CLI for registering evidence, storing its hash, checking file integrity, recording custody events, viewing custody history, and checking the consistency of the custody log.



\## Main Functional Modules



1\. Evidence Registration and Storage

2\. SHA-256 Integrity Verification

3\. Chain-of-Custody Logging and Verification



\## Technologies



\* Java 21

\* Java Standard Library

\* SHA-256

\* Local file-based persistence

\* Command-line interface



\## Scope and Limitations



This project is an educational prototype. It does not provide secure authentication, digital signatures, or tamper-proof storage. Its hash-chain verification checks consistency, but does not establish the authenticity of the recorded events.



\## Expected Outcome



A runnable Java CLI application demonstrating evidence registration, file-integrity checking, persistent records, and custody-log verification.

## Target Users

Students and learners studying cybersecurity and digital forensics who want to demonstrate basic digital-evidence integrity checking and chain-of-custody recordkeeping.


