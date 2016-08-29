package cvut.fit.domain.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Entity
public class UserRole {
    @Id
    @GeneratedValue
    private int id;

    @Type(type = "string")
    private String role;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public UserRole() {
    }

    public UserRole(String role, User user) {
        this.role = role;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
