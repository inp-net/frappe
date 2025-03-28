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
@Table(name = "majors")
public class Major {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String uid;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean discontinued = false;

	@Column(name = "teaching_units")
	@OneToMany(mappedBy = "major")
	private Collection<TeachingUnit> teachingUnits;

	@OneToMany(mappedBy = "major")
	@JsonIgnore
	private Collection<Minor> minors;

	@ManyToOne(optional = false)
	@JsonIgnore
	private School school;

	public Major() {
	}

	public Major(String uid, String name, Collection<Minor> minors, School school) {
		this.name = name;
		this.minors = minors;
		this.school = school;
	}

	public Major(String uid, String name, Collection<Minor> minors, School school, boolean discontinued) {
		this.name = name;
		this.minors = minors;
		this.school = school;
		this.discontinued = discontinued;
	}

	public long getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Minor> getMinors() {
		return minors;
	}

	public void setMinor(Collection<Minor> minors) {
		this.minors = minors;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public boolean isDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(boolean discontinued) {
		this.discontinued = discontinued;
	}
}
