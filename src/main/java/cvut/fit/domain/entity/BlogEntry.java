package cvut.fit.domain.entity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
public class BlogEntry extends AbstractBlogEntry {

    public BlogEntry() {
    }

    public BlogEntry(String title, String author, String url, String content, LocalDateTime posted, int valveId) {
        super(title, author, url, content, posted, valveId);
    }
}
