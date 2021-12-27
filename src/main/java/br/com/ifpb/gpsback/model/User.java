package br.com.ifpb.gpsback.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique=true)
    private String email;
    private String password;

    @OneToMany
    @JsonManagedReference
    private List<Task> tasks;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User( String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void remTask(Task task) {
        this.tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
