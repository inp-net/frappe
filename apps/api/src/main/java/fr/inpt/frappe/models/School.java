package fr.inpt.frappe.models;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany
    private Collection<Sector> sector;

    public School() {
    }

    public School(String name) {
        this.name = name;
    }

    public School(String name, Collection<Sector> sector) {
        this.name = name;
        this.sector = sector;
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

    public Collection<Sector> getSector() {
        return sector;
    }

    public void setSector(Collection<Sector> sector) {
        this.sector = sector;
    }
}
