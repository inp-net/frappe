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
	@OneToMany
	private Collection<TeachingUnit> teachingUnit;

	@ManyToOne
	private Major major;

	public Minor() {
	}

	public Minor(String name, Collection<TeachingUnit> teachingUnit, Major major) {
		this.name = name;
		this.teachingUnit = teachingUnit;
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

	public Collection<TeachingUnit> getTeachingUnit() {
		return teachingUnit;
	}

	public void setTeachingUnit(Collection<TeachingUnit> teachingUnit) {
		this.teachingUnit = teachingUnit;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

}
