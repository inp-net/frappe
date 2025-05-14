package fr.inpt.frappe.models;

import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users")
public class User {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	private UUID id;

	/**
	 * Unique identifier for the user
	 * Used to map oauth2 user to local user using
	 * OIDC preferred_username
	 */
	@Setter(AccessLevel.NONE)
	@Column(unique = true, nullable = false)
	private String uid;

	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String lastname;

	@Column(nullable = false)
	private int year;

	@ManyToOne
	private School school;

	@ManyToOne
	private Major major;

	@ManyToOne
	private Minor minor;

	@OneToMany(mappedBy = "author")
	@JsonIgnore
	private Collection<Document> documents;

	public User(String uid) {
		this.uid = uid;
	}

	public User(String uid, String firstname, String lastname, int year) {
		this.uid = uid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.year = year;
	}

	public User(String uid, String firstname, String lastname, int year, School school, Major major, Minor minor) {
		this.uid = uid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.year = year;
		this.school = school;
		this.major = major;
		this.minor = minor;
	}
}
