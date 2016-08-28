package cvut.fit.domain.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
//import java.sql.Date;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
public class AbstractEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer valveId;

    private String title;

    private String author;

    @Type(type = "text")
    private String content;

    private Date posted;

    private Timestamp created;


    public AbstractEntry() {
    }


    public AbstractEntry(Integer valveId, String title, String author, String content, LocalDate posted) {
        this.valveId = valveId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.posted = Date.valueOf(posted);
        this.created = Timestamp.valueOf(LocalDateTime.now());
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created.toLocalDateTime();
    }

    public void setCreated(LocalDateTime created) {
        this.created = Timestamp.valueOf(created);
    }

    public LocalDate getPosted() {
        return posted.toLocalDate();
    }

    public void setPosted(LocalDate posted) {
        this.posted = Date.valueOf(posted);
    }

    @Override
    public String toString() {
        return "AbstractEntry{" +
                "id=" + id +
                ", valveId=" + valveId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", posted=" + posted +
                ", created=" + created +
                '}';
    }
}
