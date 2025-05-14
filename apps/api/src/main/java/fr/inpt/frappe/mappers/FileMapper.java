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
import fr.inpt.frappe.controllers.dtos.FileDTO;
import fr.inpt.frappe.models.Document;
import fr.inpt.frappe.models.File;
import fr.inpt.frappe.repositories.DocumentRepository;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class FileMapper {

	@Autowired
	protected DocumentRepository documentRepository;

	@Named("mapDocument")
	public Document mapDocument(UUID docUuid) {
		return documentRepository.findById(docUuid)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "document not found"));
	}

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "document_id", target = "document", qualifiedByName = "mapDocument")
	public abstract File createFileFromDto(FileDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "document_id", target = "document", qualifiedByName = "mapDocument")
	public abstract File updateFileFromDto(FileDTO dto, @MappingTarget File file);
}
