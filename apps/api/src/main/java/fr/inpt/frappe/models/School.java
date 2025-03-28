package fr.inpt.frappe.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "schools")
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String uid;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "school")
	@JsonIgnore
	private Collection<Major> majors;

	public School() {
	}

	public School(String uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	public School(String uid, String name, Collection<Major> majors) {
		this.uid = uid;
		this.name = name;
		this.majors = majors;
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

	public Collection<Major> getMajors() {
		return majors;
	}

	public void setMajors(Collection<Major> majors) {
		this.majors = majors;
	}
}
