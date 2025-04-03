package fr.inpt.frappe.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fr.inpt.frappe.controllers.dtos.major.MajorUpdateDTO;
import fr.inpt.frappe.models.Major;

@Mapper(componentModel = "spring")
public interface MajorMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateMajorFromDto(MajorUpdateDTO dto, @MappingTarget Major major);
}
