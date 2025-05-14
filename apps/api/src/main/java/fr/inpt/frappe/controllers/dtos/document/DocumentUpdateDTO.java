package fr.inpt.frappe.controllers.dtos.document;

import java.util.Collection;
import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUpdateDTO {
	@Size(max = 255, message = "Document title must be less than 255 chars")
	private String title;

	@Size(max = 255, message = "Document description must be less than 255 chars")
	private String description;

	private UUID author;

	private Collection<Long> tags;
}
