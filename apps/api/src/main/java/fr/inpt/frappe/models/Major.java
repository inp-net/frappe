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
@Table(name = "majors")
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String short_name;

    private boolean discontinued;

    @OneToMany(mappedBy = "major")
    private Collection<Minor> minors;

    @ManyToOne
    private School school;

    public Major() {
    }

    public Major(String name, String short_name, Collection<Minor> minors, School school, boolean discontinued) {
        this.name = name;
        this.minors = minors;
        this.school = school;
        this.discontinued = discontinued;
        this.short_name = short_name;
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

    public Collection<Minor> getMinor() {
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
