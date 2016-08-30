package cvut.fit.domain.entity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Entity
public class RedditEntry extends AbstractEntry {

    public RedditEntry() {
    }

    public RedditEntry(String title, String author, String url, String content, LocalDateTime posted) {
        super(title, author, url, content, posted);
    }

}
