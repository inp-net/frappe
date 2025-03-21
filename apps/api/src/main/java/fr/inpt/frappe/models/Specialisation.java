package fr.inpt.frappe.models;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialisation")
public class Specialisation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	/** A specialisation have several education entities. */
	@OneToMany
	private Collection<EducationEntity> educationEntities;

	public Specialisation() {
	}

	public Specialisation(String name, Collection<EducationEntity> educationEntities) {
		this.name = name;
		this.educationEntities = educationEntities;
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

	public Collection<EducationEntity> getEducationEntities() {
		return educationEntities;
	}

	public void setEducationEntities(Collection<EducationEntity> educationEntities) {
		this.educationEntities = educationEntities;
	}
}
