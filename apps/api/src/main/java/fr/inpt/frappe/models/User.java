package fr.inpt.frappe.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	private UUID id;

	private String firstname;

	private String lastname;

	private int year;

	@ManyToOne
	private School school;

	@ManyToOne
	private Major major;

	@ManyToOne
	private Minor minor;

	public User() {
	}

	public User(String firstname, String lastname, int year) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.year = year;
	}

	public User(String firstname, String lastname, int year, School school, Major major, Minor minor) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.year = year;
		this.school = school;
		this.major = major;
		this.minor = minor;
	}

	public UUID getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Minor getMinor() {
		return minor;
	}

	public void setMinor(Minor minor) {
		this.minor = minor;
	}

}
