import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            EvidenceManager evidenceManager = new EvidenceManager();
            AuditLog auditLog = new AuditLog();

            boolean running = true;

            System.out.println("==================================");
            System.out.println("       EVIDENCEGUARD");
            System.out.println(" Digital Evidence Management");
            System.out.println("==================================");

            while (running) {
                showMenu();

                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {

                        case "1":
                            registerEvidence(evidenceManager);
                            break;

                        case "2":
                            viewAllEvidence(evidenceManager);
                            break;

                        case "3":
                            verifyIntegrity(evidenceManager);
                            break;

                        case "4":
                            recordCustody(evidenceManager, auditLog);
                            break;

                        case "5":
                            viewCustodyHistory(auditLog);
                            break;

                        case "6":
                            verifyCustodyLog(auditLog);
                            break;

                        case "0":
                            running = false;
                            System.out.println(
                                    "Thank you for using EvidenceGuard.");
                            break;

                        default:
                            System.out.println(
                                    "Invalid choice. Enter 0 to 6.");
                    }

                } catch (IllegalArgumentException | IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

                System.out.println();
            }

        } catch (IOException e) {
            System.out.println(
                    "Unable to load application data: "
                            + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void showMenu() {
        System.out.println("========== MAIN MENU ==========");
        System.out.println("1. Register Evidence");
        System.out.println("2. View All Evidence");
        System.out.println("3. Verify Evidence Integrity");
        System.out.println("4. Record Custody Event");
        System.out.println("5. View Custody History");
        System.out.println("6. Verify Custody Log");
        System.out.println("0. Exit");
        System.out.println("===============================");
    }

    private static void registerEvidence(
            EvidenceManager manager) throws IOException {

        System.out.println("\n--- Register Evidence ---");

        System.out.print("Enter evidence name/description: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter full file path: ");
        String filePath = scanner.nextLine().trim();

        System.out.print("Enter collector name: ");
        String collector = scanner.nextLine().trim();

        Evidence evidence = manager.registerEvidence(
                name, filePath, collector);

        System.out.println("\nEvidence registered successfully!");
        System.out.println("Evidence ID: " + evidence.getId());
        System.out.println("SHA-256: " + evidence.getSha256());
        System.out.println("Registered at: "
                + evidence.getRegisteredAt());
    }

    private static void viewAllEvidence(
            EvidenceManager manager) {

        System.out.println("\n--- All Registered Evidence ---");

        List<Evidence> list = manager.getAllEvidence();

        if (list.isEmpty()) {
            System.out.println("No evidence registered yet.");
            return;
        }

        for (Evidence e : list) {
            System.out.println("-------------------------------");
            System.out.println("ID: " + e.getId());
            System.out.println("Name: " + e.getName());
            System.out.println("Path: " + e.getFilePath());
            System.out.println("SHA-256: " + e.getSha256());
            System.out.println("Collector: " + e.getCollector());
            System.out.println("Registered: " + e.getRegisteredAt());
        }
    }

    private static void verifyIntegrity(
            EvidenceManager manager) throws IOException {

        System.out.println("\n--- Verify Evidence Integrity ---");

        System.out.print("Enter evidence ID: ");
        String id = scanner.nextLine().trim();

        boolean valid = manager.verifyIntegrity(id);

        if (valid) {
            System.out.println(
                    "INTEGRITY MATCH: File matches the registered hash.");
        } else {
            System.out.println(
                    "INTEGRITY MISMATCH: File contents have changed.");
        }
    }

    private static void recordCustody(
            EvidenceManager manager,
            AuditLog auditLog) throws IOException {

        System.out.println("\n--- Record Custody Event ---");

        System.out.print("Enter evidence ID: ");
        String id = scanner.nextLine().trim();

        if (manager.findEvidence(id) == null) {
            System.out.println("Evidence ID not found.");
            return;
        }

        System.out.print("Enter handler name: ");
        String handler = scanner.nextLine().trim();

        System.out.print("Enter action/description: ");
        String action = scanner.nextLine().trim();

        auditLog.recordEvent(id, handler, action);

        System.out.println("Custody event recorded successfully.");
    }

    private static void viewCustodyHistory(
            AuditLog auditLog) {

        System.out.println("\n--- Custody History ---");

        System.out.print("Enter evidence ID: ");
        String id = scanner.nextLine().trim();

        List<CustodyEvent> events =
                auditLog.getEventsForEvidence(id);

        if (events.isEmpty()) {
            System.out.println("No custody events found.");
            return;
        }

        for (CustodyEvent event : events) {
            System.out.println("-------------------------------");
            System.out.println(
                    "Evidence ID: " + event.getEvidenceId());
            System.out.println(
                    "Timestamp: " + event.getTimestamp());
            System.out.println(
                    "Handler: " + event.getHandler());
            System.out.println(
                    "Action: " + event.getAction());
            System.out.println(
                    "Previous hash: " + event.getPreviousHash());
            System.out.println(
                    "Event hash: " + event.getEventHash());
        }
    }

    private static void verifyCustodyLog(
            AuditLog auditLog) {

        System.out.println("\n--- Verify Custody Log ---");

        if (auditLog.verifyChain()) {
            System.out.println(
                    "CUSTODY LOG VALID: Hash chain is consistent.");
        } else {
            System.out.println(
                    "CUSTODY LOG INVALID: A chain mismatch was detected.");
        }
    }
}