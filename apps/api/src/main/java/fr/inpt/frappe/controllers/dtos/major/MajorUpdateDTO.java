package fr.inpt.frappe.controllers.dtos.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorUpdateDTO {
	private String name;
	private Boolean discontinued;
}
