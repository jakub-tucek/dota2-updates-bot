package cvut.fit.domain.entity;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
public class BlogUpdateEntry extends AbstractEntry {

    public BlogUpdateEntry() {

    }

    public BlogUpdateEntry(Integer valveId, String title, String author, String content, LocalDate posted) {
        super(valveId, title, author, content, posted);
    }
}
