package fr.inpt.frappe.models;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@Table(name = "subjects")
public class Subject {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "subject")
	private Collection<Document> documents;

	@ManyToMany
	@JoinTable(name = "subjects_teaching_units", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "teaching_unit_id"))
	private Collection<TeachingUnit> teachingUnits;

	public Subject(String name, Collection<TeachingUnit> teachingUnits) {
		this.name = name;
		this.teachingUnits = teachingUnits;
	}

}
