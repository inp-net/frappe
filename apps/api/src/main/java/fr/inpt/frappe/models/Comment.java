package fr.inpt.frappe.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	private UUID id;

	@Column(nullable = false)
	private String content;

	@ManyToOne(optional = false)
	@JsonIgnore
	private Document document;

	@ManyToOne(optional = false)
	private User user;

	public Comment(String content, Document document, User user) {
		this.content = content;
		this.document = document;
		this.user = user;
	}

}
