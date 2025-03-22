package fr.inpt.frappe.models;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Minor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	/** A minor have several teaching units. */
	@OneToMany(mappedBy = "minor")
	private Collection<TeachingUnit> teachingUnits;

	@ManyToOne
	private Major major;

	public Minor() {
	}

	public Minor(String name, Collection<TeachingUnit> teachingUnits, Major major) {
		this.name = name;
		this.teachingUnits = teachingUnits;
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

	public void setTeachingUnits(Collection<TeachingUnit> teachingUnits) {
		this.teachingUnits = teachingUnits;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

}
