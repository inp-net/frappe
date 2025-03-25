package fr.inpt.frappe.models;

import java.util.UUID;

import jakarta.persistence.Column;
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

	@Column(nullable = false)
	private String content;

	@ManyToOne(optional = false)
	private Document document;

	@ManyToOne(optional = false)
	private User user;

	public Comment(String content, Document document, User user) {
		this.content = content;
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
		return content;
	}

	public void setText(String content) {
		this.content = content;
	}

}
