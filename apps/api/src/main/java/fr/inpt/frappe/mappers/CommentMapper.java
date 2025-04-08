package fr.inpt.frappe.mappers;

import java.util.UUID;

import org.mapstruct.AnnotateWith;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import fr.inpt.frappe.annotation.GeneratedMapper;
import fr.inpt.frappe.controllers.dtos.comment.CommentCreateDTO;
import fr.inpt.frappe.controllers.dtos.comment.CommentUpdateDTO;
import fr.inpt.frappe.models.Comment;
import fr.inpt.frappe.models.Document;
import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.DocumentRepository;
import fr.inpt.frappe.repositories.UserRepository;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class CommentMapper {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private UserRepository userRepository;

	@Named("mapDocument")
	public Document mapDocument(UUID document_id) {
		return documentRepository.findById(document_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document associated not found"));
	}

	@Named("mapUser")
	public User mapUser(UUID author) {
		return userRepository.findById(author)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author (user) not found"));
	}

	@Mapping(source = "author", target = "user", qualifiedByName = "mapUser")
	@Mapping(source = "document", target = "document", qualifiedByName = "mapDocument")
	public abstract Comment createDocumentFromDto(CommentCreateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "document", ignore = true)
	public abstract Comment updateCommentFromDto(CommentUpdateDTO dto, @MappingTarget Comment comment);
}
