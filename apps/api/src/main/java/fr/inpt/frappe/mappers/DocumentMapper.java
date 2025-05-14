package fr.inpt.frappe.mappers;

import java.util.Collection;
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
import fr.inpt.frappe.controllers.dtos.document.DocumentCreateDTO;
import fr.inpt.frappe.controllers.dtos.document.DocumentUpdateDTO;
import fr.inpt.frappe.models.Document;
import fr.inpt.frappe.models.Subject;
import fr.inpt.frappe.models.Tag;
import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.SubjectRepository;
import fr.inpt.frappe.repositories.TagRepository;
import fr.inpt.frappe.repositories.UserRepository;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class DocumentMapper {

	@Autowired
	protected UserRepository users;

	@Autowired
	protected TagRepository tagRepository;

	@Autowired
	protected SubjectRepository subjectRepository;

	@Named("mapAuthor")
	public User mapAuthor(UUID userId) {
		return users.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document author not found"));
	}

	@Named("mapTags")
	public Collection<Tag> mapTags(Collection<Long> tags) {
		return tagRepository.findAllById(tags);
	}

	@Named("mapSubject")
	public Subject mapSubject(Long subjectId) {
		return subjectRepository.findById(subjectId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document subject not found"));
	}

	@Mapping(source = "author", target = "author", qualifiedByName = "mapAuthor")
	@Mapping(source = "tags", target = "tags", qualifiedByName = "mapTags")
	@Mapping(source = "subject_id", target = "subject", qualifiedByName = "mapSubject")
	@Mapping(target = "comments", ignore = true)
	@Mapping(target = "files", ignore = true)
	public abstract Document createDocumentFromDto(DocumentCreateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "author", target = "author", qualifiedByName = "mapAuthor")
	@Mapping(source = "tags", target = "tags", qualifiedByName = "mapTags")
	@Mapping(target = "subject", ignore = true)
	@Mapping(target = "comments", ignore = true)
	@Mapping(target = "files", ignore = true)
	public abstract void updateDocumentFromDto(DocumentUpdateDTO dto, @MappingTarget Document document);
}
