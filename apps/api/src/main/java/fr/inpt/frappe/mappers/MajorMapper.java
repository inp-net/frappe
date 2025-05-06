package fr.inpt.frappe.mappers;

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
import fr.inpt.frappe.controllers.dtos.major.MajorCreateDTO;
import fr.inpt.frappe.controllers.dtos.major.MajorUpdateDTO;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.repositories.SchoolRepository;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class MajorMapper {

	@Autowired
	protected SchoolRepository schoolRepository;

	@Named("mapSchool")
	public School mapUser(String school_uid) {
		return schoolRepository.findByUid(school_uid)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));
	}

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "school_uid", target = "school", qualifiedByName = "mapSchool")
	@Mapping(target = "minors", ignore = true)
	@Mapping(target = "teachingUnits", ignore = true)
	@Mapping(target = "discontinued", ignore = true)
	public abstract Major createMajorFromDto(MajorCreateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "uid", ignore = true)
	@Mapping(target = "school", ignore = true)
	@Mapping(target = "minors", ignore = true)
	@Mapping(target = "teachingUnits", ignore = true)
	public abstract void updateMajorFromDto(MajorUpdateDTO dto, @MappingTarget Major major);
}
