package fr.inpt.frappe.models;

import java.util.Collection;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "idcourse")
    private long idCourse;

    @ElementCollection
    private Collection<Long> listIdTags;

    public Document(String title, long idCourse, Collection<Long> listIdTags) {
        this.title = title;
        this.idCourse = idCourse;
        this.listIdTags = listIdTags;
    }

    public Document() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(long idCourse) {
        this.idCourse = idCourse;
    }

    public Collection<Long> getListIdTags() {
        return listIdTags;
    }

    public void setListIdTags(Collection<Long> listIdTags) {
        this.listIdTags = listIdTags;
    }

}
