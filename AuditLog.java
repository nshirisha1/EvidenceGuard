import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AuditLog {

    private static final Path LOG_FILE =
            Path.of("data", "custody.txt");

    private static final String FIRST_HASH = "GENESIS";

    private List<CustodyEvent> events = new ArrayList<>();

    public AuditLog() throws IOException {
        loadEvents();
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(
                value.getBytes(StandardCharsets.UTF_8));
    }

    private String decode(String value) {
        return new String(
                Base64.getDecoder().decode(value),
                StandardCharsets.UTF_8);
    }

    private String calculateHash(String content) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] bytes = digest.digest(
                    content.getBytes(StandardCharsets.UTF_8));

            return java.util.HexFormat.of().formatHex(bytes);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 is unavailable", e);
        }
    }

    private String eventContent(
            String evidenceId,
            String timestamp,
            String handler,
            String action,
            String previousHash) {

        return evidenceId + "\n"
                + timestamp + "\n"
                + handler + "\n"
                + action + "\n"
                + previousHash;
    }

    public void recordEvent(
            String evidenceId,
            String handler,
            String action) throws IOException {

        if (evidenceId == null || evidenceId.isBlank()
                || handler == null || handler.isBlank()
                || action == null || action.isBlank()) {

            throw new IllegalArgumentException(
                    "Evidence ID, handler, and action are required.");
        }

        LocalDateTime timestamp = LocalDateTime.now();

        String previousHash = FIRST_HASH;

        if (!events.isEmpty()) {
            previousHash =
                    events.get(events.size() - 1).getEventHash();
        }

        String content = eventContent(
                evidenceId,
                timestamp.toString(),
                handler,
                action,
                previousHash);

        String eventHash = calculateHash(content);

        CustodyEvent event = new CustodyEvent(
                evidenceId,
                timestamp,
                handler,
                action,
                previousHash,
                eventHash);

        events.add(event);

        try {
            saveEvents();
        } catch (IOException e) {
            events.remove(event);
            throw e;
        }
    }

    public List<CustodyEvent> getEventsForEvidence(
            String evidenceId) {

        List<CustodyEvent> result = new ArrayList<>();

        for (CustodyEvent event : events) {
            if (event.getEvidenceId().equalsIgnoreCase(
                    evidenceId.trim())) {
                result.add(event);
            }
        }

        return result;
    }

    public boolean verifyChain() {
        String expectedPreviousHash = FIRST_HASH;

        for (CustodyEvent event : events) {

            String content = eventContent(
                    event.getEvidenceId(),
                    event.getTimestamp().toString(),
                    event.getHandler(),
                    event.getAction(),
                    expectedPreviousHash);

            String expectedHash = calculateHash(content);

            if (!event.getPreviousHash().equals(
                    expectedPreviousHash)) {
                return false;
            }

            if (!event.getEventHash().equals(expectedHash)) {
                return false;
            }

            expectedPreviousHash = event.getEventHash();
        }

        return true;
    }

    private void saveEvents() throws IOException {
        Files.createDirectories(LOG_FILE.getParent());

        List<String> lines = new ArrayList<>();

        for (CustodyEvent event : events) {
            lines.add(String.join("|",
                    encode(event.getEvidenceId()),
                    encode(event.getTimestamp().toString()),
                    encode(event.getHandler()),
                    encode(event.getAction()),
                    encode(event.getPreviousHash()),
                    encode(event.getEventHash())
            ));
        }

        Files.write(
                LOG_FILE,
                lines,
                StandardCharsets.UTF_8);
    }

    private void loadEvents() throws IOException {
        events.clear();

        if (!Files.exists(LOG_FILE)) {
            return;
        }

        List<String> lines = Files.readAllLines(
                LOG_FILE,
                StandardCharsets.UTF_8);

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\\|", -1);

            if (parts.length != 6) {
                throw new IOException(
                        "Invalid custody record in data file.");
            }

            try {
                events.add(new CustodyEvent(
                        decode(parts[0]),
                        LocalDateTime.parse(decode(parts[1])),
                        decode(parts[2]),
                        decode(parts[3]),
                        decode(parts[4]),
                        decode(parts[5])
                ));
            } catch (RuntimeException e) {
                throw new IOException(
                        "Could not read custody record.", e);
            }
        }
    }
}