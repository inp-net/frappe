package fr.inpt.frappe.models;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "minors")
public class Minor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String short_name;

	/** A minor have several teaching units. */
	@OneToMany(mappedBy = "minor")
	private Collection<TeachingUnit> teaching_units;

	@ManyToOne
	private Major major;

	public Minor() {
	}

	public Minor(String name, String short_name, Collection<TeachingUnit> teaching_units, Major major) {
		this.name = name;
		this.short_name = short_name;
		this.teaching_units = teaching_units;
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

	public String getShortName() {
		return short_name;
	}

	public void setShortName(String short_name) {
		this.short_name = short_name;
	}

	public Collection<TeachingUnit> getTeaching_units() {
		return teaching_units;
	}

	public void setTeaching_units(Collection<TeachingUnit> teaching_units) {
		this.teaching_units = teaching_units;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

}
