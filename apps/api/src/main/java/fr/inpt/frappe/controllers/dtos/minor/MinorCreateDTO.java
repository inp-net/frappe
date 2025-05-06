package fr.inpt.frappe.controllers.dtos.minor;

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
public class MinorCreateDTO {

	@NotNull(message = "Minor name is required")
	@NotBlank(message = "Minor name should not be empty")
	@Size(max = 255, message = "Minor name must be less than 255 chars")
	private String name;

	@NotNull(message = "Minor should be linked to a major")
	@NotBlank(message = "Major id should not be empty")
	@Size(max = 255, message = "Major id must be less than 255 chars")
	private String major_uid;
}
