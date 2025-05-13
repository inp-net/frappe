package fr.inpt.frappe.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "majors")
public class Major {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String uid;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean discontinued = false;

	@ManyToMany(mappedBy = "majors")
	@JsonIgnore
	private Collection<TeachingUnit> teachingUnits;

	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "major")
	@JsonIgnore
	private Collection<Minor> minors;

	@ManyToOne(optional = false)
	@JsonIgnore
	private School school;

	public Major(String uid, String name, School school) {
		this.uid = uid;
		this.name = name;
		this.school = school;
	}

	public Major(String uid, String name, School school, boolean discontinued) {
		this.uid = uid;
		this.name = name;
		this.school = school;
		this.discontinued = discontinued;
	}
}
