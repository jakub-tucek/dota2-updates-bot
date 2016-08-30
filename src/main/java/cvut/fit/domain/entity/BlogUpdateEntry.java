package cvut.fit.domain.entity;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
public class BlogUpdateEntry extends BlogEntry {
    public BlogUpdateEntry() {
    }

    public BlogUpdateEntry(String title, String author, String url, String content, LocalDate posted, int valveId) {
        super(title, author, url, content, posted, valveId);
    }
}
