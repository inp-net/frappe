package fr.inpt.frappe.models;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int semester;

    private boolean forApprentices;

    @OneToMany
    private Collection<Document> documents;

    @ManyToOne
    private TeachingUnit teachingUnit;

    public Subject() {
    }

    public Subject(String name, int semester, boolean forApprentices, Collection<Document> documents,
            TeachingUnit teachingUnit) {
        this.name = name;
        this.documents = documents;
        this.teachingUnit = teachingUnit;
        this.semester = semester;
        this.forApprentices = forApprentices;
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

    public boolean isForApprentices() {
        return forApprentices;
    }

    public void setForApprentices(boolean forApprentices) {
        this.forApprentices = forApprentices;
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

    public TeachingUnit getTeachingUnit() {
        return teachingUnit;
    }

    public void setTeachingUnit(TeachingUnit teachingUnit) {
        this.teachingUnit = teachingUnit;
    }

}
