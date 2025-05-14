package fr.inpt.frappe.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "files")
@NoArgsConstructor
public class File {
	
	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	private UUID id;

	private String name;

	private String extension;

	@ManyToOne
	@JoinColumn(name = "document_id")
	@JsonIgnore
	private Document document;

	public File(String name, String extension, Document document) {
		this.name = name;
		this.extension = extension;
		this.document = document;
	}
}
