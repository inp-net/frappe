package fr.inpt.frappe.controllers.dtos.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorCreateDTO {
	private String uid;
	private String name;
	private String school_uid;
}
