package fr.inpt.frappe.controllers.dtos.teaching_units;

import java.util.Collection;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeachingUnitUpdateDTO {

	@Size(max = 255, message = "Teaching unit name must be less than 255 chars")
	private String name;
	
	private Collection<Long> majors;
	private Collection<Long> minors;
}
