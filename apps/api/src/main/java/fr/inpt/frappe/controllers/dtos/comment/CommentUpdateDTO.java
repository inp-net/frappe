package fr.inpt.frappe.controllers.dtos.comment;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDTO {

	@Size(max = 255, message = "comment content must be less than 255 chars")
	private String content;
}
