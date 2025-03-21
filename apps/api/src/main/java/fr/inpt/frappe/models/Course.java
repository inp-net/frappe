package fr.inpt.frappe.models;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "educationentity")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
    private String name;
    
    @OneToMany
    private Collection<Document> documents;

    public Course() {}

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Collection<Document> getCourses() {
        return documents;
    }

    public void setCourses(Collection<Course> documents) {
        this.documents = documents;
    }
}
