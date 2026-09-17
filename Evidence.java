import java.time.LocalDateTime;

public class Evidence {

    private String id;
    private String name;
    private String filePath;
    private String sha256;
    private String collector;
    private LocalDateTime registeredAt;

    public Evidence(String id, String name, String filePath,
                    String sha256, String collector,
                    LocalDateTime registeredAt) {

        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.sha256 = sha256;
        this.collector = collector;
        this.registeredAt = registeredAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getSha256() {
        return sha256;
    }

    public String getCollector() {
        return collector;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}