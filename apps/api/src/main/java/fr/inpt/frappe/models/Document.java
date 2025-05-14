package fr.inpt.frappe.models;

import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@Table(name = "documents")
public class Document {
	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	private UUID id;

	@Column(nullable = false)
	private String title;

	private String description;

	@ManyToOne(optional = false)
	@JsonIgnore
	private Subject subject;

	@ManyToMany(mappedBy = "documents", fetch = FetchType.EAGER)
	private Collection<Tag> tags;

	@OneToMany(mappedBy = "document")
	private Collection<Comment> comments;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "document")
	private Collection<File> files;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinTable(name = "documents_user", joinColumns = @JoinColumn(name = "document_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private User author;

	public Document(String title, Subject subject) {
		this.title = title;
		this.subject = subject;
	}

	public Document(String title, String description, Subject subject) {
		this.title = title;
		this.description = description;
		this.subject = subject;
	}
}
