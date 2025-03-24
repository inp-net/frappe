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
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int semester;

    @OneToMany(mappedBy = "subject")
    private Collection<Document> documents;

    @ManyToOne
    private TeachingUnit teaching_unit;

    public Subject() {
    }

    public Subject(String name, int semester, Collection<Document> documents,
            TeachingUnit teaching_unit) {
        this.name = name;
        this.documents = documents;
        this.teaching_unit = teaching_unit;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

    public TeachingUnit getTeaching_unit() {
        return teaching_unit;
    }

    public void setTeaching_unit(TeachingUnit teaching_unit) {
        this.teaching_unit = teaching_unit;
    }

}
