package fr.inpt.frappe.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    @ManyToOne
    private Document document;

    @ManyToOne
    private User user;

    public Comment() {
    }

    public Comment(String text, Document document, User user) {
        this.text = text;
        this.document = document;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public Document getDocument() {
        return document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
