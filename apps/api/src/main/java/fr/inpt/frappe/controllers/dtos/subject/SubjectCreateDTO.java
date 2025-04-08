package fr.inpt.frappe.controllers.dtos.subject;

import java.util.Collection;

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
public class SubjectCreateDTO {

	@NotNull(message = "Document title is required")
	@NotBlank(message = "Document title should not be empty")
	@Size(max = 255, message = "Document title must be less than 255 chars")
	private String name;

	@Size(min = 1, message = "A subject should have at least a teaching unit to be created")
	private Collection<Long> teaching_units;
}
