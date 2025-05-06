package fr.inpt.frappe.controllers.dtos;

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
public class TagDTO {

	@NotNull(message = "Tag name is required")
	@NotBlank(message = "Tag name should not be empty")
	@Size(max = 255, message = "Tag name must be less than 255 chars")
	private String name;
}
