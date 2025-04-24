package fr.inpt.frappe.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "minors")
public class Minor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	/** A minor have several teaching units. */
	@Column(name = "teaching_units")
	@ManyToMany(mappedBy = "minors")
	private Collection<TeachingUnit> teachingUnits;

	@ManyToOne(optional = false)
	@JsonIgnore
	private Major major;

	public Minor() {
	}

	public Minor(String name, Major major) {
		this.name = name;
		this.major = major;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<TeachingUnit> getTeachingUnits() {
		return teachingUnits;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

}
