package com.example.my_bullet_journal.models.entities;



import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String username;
    private String email;
    private String password;
    private Set<DailyTask> tasks  = new HashSet<>();
    private Set<Role> roles;
    private List<Expense> expenses;
    public User() {
    }

    @Column(unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @Column(unique = true,nullable = false)
    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @OneToMany(mappedBy = "user")
    public Set<DailyTask> getTasks() {
        return tasks;
    }

    public User setTasks(Set<DailyTask> tasks) {
        this.tasks = tasks;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    @OneToMany(mappedBy = "user")
    public List<Expense> getExpenses() {
        return expenses;
    }

    public User setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }
}
