package cvut.fit.domain.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

//import java.sql.Date;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Entity
public class AbstractEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private String author;

    private String url;

    @Type(type = "text")
    private String content;

    private Date posted;

    private Timestamp created;

    public AbstractEntry() {
    }

    public AbstractEntry(String title, String author, String url, String content, LocalDate posted) {
        this.title = title;
        this.author = author;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

}
