package fr.inpt.frappe.models;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstname;

	private String lastname;

	private int year;

	@ManyToOne
	private School school;

	@ManyToOne
	private Sector sector;

	@ManyToOne
	private Specialisation specialisation;

	public User() {
	}

	public User(String firsname, String lastname, int year) {
		this.firstname = firsname;
		this.lastname = lastname;
		this.year = year;
	}

	public User(String firstname, String lastname, int year, School school, Sector sector,
			Specialisation specialisation) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.year = year;
		this.school = school;
		this.sector = sector;
		this.specialisation = specialisation;
	}

	public long getId() {
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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Specialisation getSpecialisation() {
		return specialisation;
	}

	public void setSpecialisation(Specialisation specialisation) {
		this.specialisation = specialisation;
	}

}
