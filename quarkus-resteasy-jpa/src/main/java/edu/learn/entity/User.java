package edu.learn.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long userId;

    @NotNull(message = "{not.null.message}")
    @Length(min = 3, max = 255, message = "{invalid.length.message}")
    public String username;

    @Length(min = 3, max = 255, message = "{invalid.length.message}")
    public String password;

    @Length(min = 3, max = 255, message = "{invalid.length.message}")
    public String role;

    public User() {}

    public User(Long userId) {
        this.userId = userId;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId()) && getUsername().equals(user.getUsername()) && getPassword().equals(user.getPassword()) && getRole().equals(user.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getPassword(), getRole());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("role='" + role + "'")
                .toString();
    }
}
