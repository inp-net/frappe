package fr.inpt.frappe.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private boolean discontinued;

    @OneToMany
    private Minor minor;

    @ManyToOne
    private School school;

    public Major() {
    }

    public Major(String name, Minor minor, School school, boolean discontinued) {
        this.name = name;
        this.minor = minor;
        this.school = school;
        this.discontinued = discontinued;
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

    public Minor getMinor() {
        return minor;
    }

    public void setMinor(Minor minor) {
        this.minor = minor;
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
