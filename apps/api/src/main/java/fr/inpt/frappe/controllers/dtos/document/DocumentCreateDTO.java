package fr.inpt.frappe.controllers.dtos.document;

import java.util.Collection;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentCreateDTO {
	@NotNull(message = "Document title is required")
	@NotBlank(message = "Document title should not be empty")
	@Size(max = 255, message = "Document title must be less than 255 chars")
	private String title;

	@NotNull(message = "The year of the document is required")
	private int year;

	@Size(max = 255, message = "Document description must be less than 255 chars")
	private String description;

	@NotNull(message = "Document should be linked to a subject")
	private Long subject_id;

	private UUID author;

	private Collection<Long> tags;
}
