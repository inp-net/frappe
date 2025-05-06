package fr.inpt.frappe.controllers.dtos.school;

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
public class SchoolCreateDTO {

	@NotNull(message = "School uid is required")
	@NotBlank(message = "School uid should not be empty")
	@Size(max = 255, message = "School uid must be less than 255 chars")
	private String uid;

	@NotNull(message = "School name is required")
	@NotBlank(message = "School name should not be empty")
	@Size(max = 255, message = "School name must be less than 255 chars")
	private String name;
}
