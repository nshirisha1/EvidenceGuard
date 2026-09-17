import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvidenceManager {

    private List<Evidence> evidenceList;

    public EvidenceManager() throws IOException {
        evidenceList = StorageManager.loadEvidence();
    }

    public Evidence registerEvidence(
            String name,
            String filePath,
            String collector) throws IOException {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Evidence name cannot be empty.");
        }

        if (collector == null || collector.isBlank()) {
            throw new IllegalArgumentException(
                    "Collector name cannot be empty.");
        }

        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException(
                    "File path cannot be empty.");
        }

        Path path = Path.of(filePath);

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new IOException(
                    "File does not exist or is not a regular file.");
        }

        String hash = HashUtils.calculateSHA256(filePath);

        String id = generateEvidenceId();

        Evidence evidence = new Evidence(
                id,
                name,
                path.toAbsolutePath().normalize().toString(),
                hash,
                collector,
                LocalDateTime.now()
        );

        evidenceList.add(evidence);

        try {
            StorageManager.saveEvidence(evidenceList);
        } catch (IOException e) {
            evidenceList.remove(evidence);
            throw e;
        }

        return evidence;
    }

    private String generateEvidenceId() {
        int nextNumber = evidenceList.size() + 1;

        String id;

        do {
            id = String.format("EV-%04d", nextNumber);
            nextNumber++;
        } while (findEvidence(id) != null);

        return id;
    }

    public List<Evidence> getAllEvidence() {
        return new ArrayList<>(evidenceList);
    }

    public Evidence findEvidence(String id) {
        if (id == null) {
            return null;
        }

        for (Evidence evidence : evidenceList) {
            if (evidence.getId().equalsIgnoreCase(id.trim())) {
                return evidence;
            }
        }

        return null;
    }

    public boolean verifyIntegrity(String id) throws IOException {
        Evidence evidence = findEvidence(id);

        if (evidence == null) {
            throw new IllegalArgumentException(
                    "Evidence ID not found.");
        }

        Path path = Path.of(evidence.getFilePath());

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new IOException(
                    "Evidence file is missing.");
        }

        String currentHash =
                HashUtils.calculateSHA256(evidence.getFilePath());

        return currentHash.equalsIgnoreCase(
                evidence.getSha256());
    }
}