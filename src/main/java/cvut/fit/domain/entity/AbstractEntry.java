package cvut.fit.domain.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
@Inheritance
public abstract class AbstractEntry {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer valveId;

    private String title;

    private String content;

    private Date posted;
    private Date created;


    public AbstractEntry() {
    }


    public AbstractEntry(Integer valveId, String title, String content, Date posted, Date created) {

        this.valveId = valveId;
        this.title = title;
        this.content = content;
        this.posted = posted;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValveId() {
        return valveId;
    }

    public void setValveId(Integer valveId) {
        this.valveId = valveId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }
}
