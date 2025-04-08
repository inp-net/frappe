package fr.inpt.frappe.controllers.dtos.comment;

import java.util.UUID;

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
public class CommentCreateDTO {

	@NotNull(message = "comment content is required")
	@NotBlank(message = "comment content should not be empty")
	@Size(max = 255, message = "comment content must be less than 255 chars")
	private String content;

	@NotNull(message = "a comment should be linked to a document")
	private UUID document;

	private UUID author;
}
