package fr.inpt.frappe.mappers;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.inpt.frappe.annotation.GeneratedMapper;
import fr.inpt.frappe.controllers.dtos.TagDTO;
import fr.inpt.frappe.models.Tag;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class TagMapper {

	@Mapping(target = "documents", ignore = true)
	public abstract Tag createTagFromDto(TagDTO dto);

	@Mapping(target = "documents", ignore = true)
	public abstract Tag updateTagFromDto(TagDTO dto, @MappingTarget Tag tag);
}
