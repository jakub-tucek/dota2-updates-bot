package cvut.fit.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import java.time.LocalDate;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
public class BlogEntry extends AbstractEntry {

    private String url;


    public BlogEntry() {
    }

    public BlogEntry(Integer valveId, String title, String author, String content, LocalDate posted, String url) {
        super(valveId, title, author, content, posted);
        this.url = url;
    }

    public BlogEntry(Integer valveId, String title, String author, String content, LocalDate posted) {
        super(valveId, title, author, content, posted);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
