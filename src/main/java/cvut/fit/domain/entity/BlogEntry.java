package cvut.fit.domain.entity;

import javax.persistence.Entity;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
public class BlogEntry extends AbstractEntry {
    public BlogEntry() {
    }

    public BlogEntry(Integer valveId, String title, String content, LocalDate posted) {
        super(valveId, title, content, posted);
    }
}
