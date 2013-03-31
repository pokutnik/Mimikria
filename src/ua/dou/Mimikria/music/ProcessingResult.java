package ua.dou.Mimikria.music;

/**
 * User: David
 * Date: 31.03.13
 * Time: 11:34
 */
public class ProcessingResult {
    private boolean processed;
    private String link;
    private String id;

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
