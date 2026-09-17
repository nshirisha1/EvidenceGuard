import java.time.LocalDateTime;

public class CustodyEvent {

    private String evidenceId;
    private LocalDateTime timestamp;
    private String handler;
    private String action;
    private String previousHash;
    private String eventHash;

    public CustodyEvent(
            String evidenceId,
            LocalDateTime timestamp,
            String handler,
            String action,
            String previousHash,
            String eventHash) {

        this.evidenceId = evidenceId;
        this.timestamp = timestamp;
        this.handler = handler;
        this.action = action;
        this.previousHash = previousHash;
        this.eventHash = eventHash;
    }

    public String getEvidenceId() {
        return evidenceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getHandler() {
        return handler;
    }

    public String getAction() {
        return action;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getEventHash() {
        return eventHash;
    }
}