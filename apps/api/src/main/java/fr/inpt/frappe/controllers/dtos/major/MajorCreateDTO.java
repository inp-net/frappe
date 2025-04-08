package fr.inpt.frappe.controllers.dtos.major;

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
public class MajorCreateDTO {

	@NotNull(message = "Major uid is required")
	@NotBlank(message = "Major uid should not be empty")
	@Size(max = 255, message = "Major uid must be less than 255 chars")
	private String uid;

	@NotNull(message = "Major name is required")
	@NotBlank(message = "Major name should not be empty")
	@Size(max = 255, message = "Major name must be less than 255 chars")
	private String name;

	@NotNull(message = "major should be linked to a school")
	@NotBlank(message = "School uid should not be empty")
	@Size(max = 255, message = "School uid must be less than 255 chars")
	private String school_uid;
}
