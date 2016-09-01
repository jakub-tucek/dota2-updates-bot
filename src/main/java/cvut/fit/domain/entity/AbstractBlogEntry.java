package cvut.fit.domain.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@MappedSuperclass
public class AbstractBlogEntry extends AbstractEntry {
    private int valveId;


    public AbstractBlogEntry() {
    }

    public AbstractBlogEntry(String title, String author, String url, String content, LocalDateTime posted, int valveId) {
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
