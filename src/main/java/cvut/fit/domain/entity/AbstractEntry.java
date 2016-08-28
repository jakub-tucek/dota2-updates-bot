package cvut.fit.domain.entity;

import javax.persistence.*;
//import java.sql.Date;
import java.time.LocalDate;
import java.util.Date;

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

    private LocalDate posted;
    private LocalDate created;


    public AbstractEntry() {
    }


    public AbstractEntry(Integer valveId, String title, String content, LocalDate posted) {

        this.valveId = valveId;
        this.title = title;
        this.content = content;
        this.posted = posted;
        this.created = LocalDate.now();
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getPosted() {
        return posted;
    }

    public void setPosted(LocalDate posted) {
        this.posted = posted;
    }
}
