package br.com.ifpb.gpsback.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

@Entity
public class Task implements  Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String status;
    private String date;

    @ManyToOne
    @JsonBackReference
    private User user;

    public Task() {
    }

    public Task(Long id, String title, String description, String status, String date) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public Task( String title, String description, String status, String date) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public Task(Long id, String title, String description, String status, String date, User user) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
        this.user = user;
    }

    public Task( String title, String description, String status, String date, User user) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Task clone() throws CloneNotSupportedException { return (Task) super.clone();}

}