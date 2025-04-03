package fr.inpt.frappe.mappers;

import org.mapstruct.AnnotateWith;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fr.inpt.frappe.annotation.GeneratedMapper;
import fr.inpt.frappe.controllers.dtos.major.MajorUpdateDTO;
import fr.inpt.frappe.models.Major;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public interface MajorMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "school", ignore = true)
    @Mapping(target = "minors", ignore = true)
	void updateMajorFromDto(MajorUpdateDTO dto, @MappingTarget Major major);
}
