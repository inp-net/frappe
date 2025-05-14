package fr.inpt.frappe.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@Table(name = "minors")
public class Minor {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	/** A minor have several teaching units. */
	@Setter(AccessLevel.NONE)
	@ManyToMany(mappedBy = "minors")
	@JsonIgnore
	private Collection<TeachingUnit> teachingUnits;

	@ManyToOne(optional = false)
	private Major major;

	public Minor(String name, Major major) {
		this.name = name;
		this.major = major;
	}
}
