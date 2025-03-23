package fr.inpt.frappe.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

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

    public UUID getId() {
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
