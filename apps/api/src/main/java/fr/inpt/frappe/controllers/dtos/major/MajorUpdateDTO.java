package fr.inpt.frappe.controllers.dtos.major;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorUpdateDTO {

	@Size(max = 255, message = "Major name must be less than 255 chars")
	private String name;

	@Size(max = 255, message = "Major uid must be less than 255 chars")
	private String uid;

	@Size(max = 255, message = "School uid must be less than 255 chars")
	private String school_uid;

	private Boolean discontinued;
}
