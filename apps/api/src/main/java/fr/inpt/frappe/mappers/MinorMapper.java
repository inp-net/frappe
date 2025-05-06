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
import fr.inpt.frappe.controllers.dtos.minor.MinorCreateDTO;
import fr.inpt.frappe.controllers.dtos.minor.MinorUpdateDTO;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.repositories.MajorRepository;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class MinorMapper {

	@Autowired
	protected MajorRepository majorRepository;

	@Named("mapMajor")
	public Major mapUser(String major_uid) {
		return majorRepository.findByUid(major_uid)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Major not found"));
	}

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "major_uid", target = "major", qualifiedByName = "mapMajor")
	@Mapping(target = "teachingUnits", ignore = true)
	public abstract Minor createMinorFromDto(MinorCreateDTO dto);

	@Mapping(target = "major", ignore = true)
	@Mapping(target = "teachingUnits", ignore = true)
	public abstract void updateMinorFromDto(MinorUpdateDTO dto, @MappingTarget Minor minor);
}
