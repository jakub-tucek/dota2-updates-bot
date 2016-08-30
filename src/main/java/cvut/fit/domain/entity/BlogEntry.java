package cvut.fit.domain.entity;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
public class BlogEntry extends AbstractEntry {

    private int valveId;


    public BlogEntry() {
    }

    public BlogEntry(String title, String author, String url, String content, LocalDate posted, int valveId) {
        super(title, author, url, content, posted);
        this.valveId = valveId;
    }

    public int getValveId() {
        return valveId;
    }

    public void setValveId(int valveId) {
        this.valveId = valveId;
    }
}
