package cvut.fit.domain.entity;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Entity
public class RedditEntry extends AbstractEntry {

    private Timestamp edited;

    private String subReddit;

    public RedditEntry() {
    }

    public RedditEntry(String title, String author, String url, String content, LocalDateTime posted, LocalDateTime edited, String subReddit) {
        super(title, author, url, content, posted);
        this.edited = Timestamp.valueOf(edited);
        this.subReddit = subReddit;
    }

    public RedditEntry(String title, String author, String url, String content, LocalDateTime posted, String subReddit) {
        super(title, author, url, content, posted);
        this.subReddit = subReddit;
        this.edited = Timestamp.valueOf(posted);
    }

    public LocalDateTime getEdited() {
        return edited.toLocalDateTime();
    }

    public void setEdited(LocalDateTime edited) {
        this.edited = Timestamp.valueOf(edited);
    }

    public void setEdited(Timestamp edited) {
        this.edited = edited;
    }

    public String getSubReddit() {
        return subReddit;
    }

    public void setSubReddit(String subReddit) {
        this.subReddit = subReddit;
    }
}
