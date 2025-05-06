package fr.inpt.frappe.mappers;

import org.mapstruct.AnnotateWith;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fr.inpt.frappe.annotation.GeneratedMapper;
import fr.inpt.frappe.controllers.dtos.school.SchoolCreateDTO;
import fr.inpt.frappe.controllers.dtos.school.SchoolUpdateDTO;
import fr.inpt.frappe.models.School;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class SchoolMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "majors", ignore = true)
	public abstract School createSchoolFromDto(SchoolCreateDTO dto);

	@Mapping(target = "uid", ignore = true)
    @Mapping(target = "majors", ignore = true)
	public abstract School updateSchoolFromDto(SchoolUpdateDTO dto, @MappingTarget School school);
}
