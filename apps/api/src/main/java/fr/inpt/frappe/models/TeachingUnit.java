package fr.inpt.frappe.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "teaching_units")
public class TeachingUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	/** A teching unit have several subjects. */
	@OneToMany(mappedBy = "teachingUnit")
	@JsonIgnore
	private Collection<Subject> subjects;

	@ManyToOne
	private Minor minor;

	@ManyToOne
	private Major major;

	public TeachingUnit() {
	}

	public TeachingUnit(String name, Collection<Subject> subjects, Minor minor) {
		this.name = name;
		this.subjects = subjects;
		this.minor = minor;
	}

	public TeachingUnit(String name, Collection<Subject> subjects, Major major) {
		this.name = name;
		this.subjects = subjects;
		this.major = major;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Collection<Subject> subjects) {
		this.subjects = subjects;
	}

	public Minor getMinor() {
		return minor;
	}

	public void setMinor(Minor minor) {
		this.minor = minor;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}
}
