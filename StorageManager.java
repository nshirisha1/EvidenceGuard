import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class StorageManager {

    private static final Path DATA_FILE =
            Path.of("data", "evidence.txt");

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(
                value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        return new String(
                Base64.getDecoder().decode(value),
                StandardCharsets.UTF_8);
    }

    public static void saveEvidence(List<Evidence> evidenceList)
            throws IOException {

        Files.createDirectories(DATA_FILE.getParent());

        List<String> lines = new ArrayList<>();

        for (Evidence e : evidenceList) {
            String line = String.join("|",
                    encode(e.getId()),
                    encode(e.getName()),
                    encode(e.getFilePath()),
                    encode(e.getSha256()),
                    encode(e.getCollector()),
                    encode(e.getRegisteredAt().toString())
            );

            lines.add(line);
        }

        Files.write(
                DATA_FILE,
                lines,
                StandardCharsets.UTF_8
        );
    }

    public static List<Evidence> loadEvidence()
            throws IOException {

        List<Evidence> evidenceList = new ArrayList<>();

        if (!Files.exists(DATA_FILE)) {
            return evidenceList;
        }

        List<String> lines = Files.readAllLines(
                DATA_FILE,
                StandardCharsets.UTF_8
        );

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\\|", -1);

            if (parts.length != 6) {
                throw new IOException(
                        "Invalid evidence record in data file.");
            }

            Evidence evidence = new Evidence(
                    decode(parts[0]),
                    decode(parts[1]),
                    decode(parts[2]),
                    decode(parts[3]),
                    decode(parts[4]),
                    LocalDateTime.parse(decode(parts[5]))
            );

            evidenceList.add(evidence);
        }

        return evidenceList;
    }
}