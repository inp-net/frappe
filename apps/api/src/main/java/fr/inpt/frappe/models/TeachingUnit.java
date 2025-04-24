package fr.inpt.frappe.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

@Data
@Entity
@NoArgsConstructor
@Table(name = "teaching_units")
public class TeachingUnit {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	/** A teaching unit have several subjects. */
	@ManyToMany(mappedBy = "teachingUnits")
	@JsonIgnore
	private Collection<Subject> subjects;

	@ManyToMany
	private Collection<Minor> minors;

	@ManyToMany
	private Collection<Major> majors;

	public TeachingUnit(String name, Collection<Minor> minors, Collection<Major> majors) {
		this.name = name;
		this.minors = minors;
		this.majors = majors;
	}

}
