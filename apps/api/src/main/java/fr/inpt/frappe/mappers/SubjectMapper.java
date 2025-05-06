package fr.inpt.frappe.mappers;

import java.util.Collection;

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
import fr.inpt.frappe.controllers.dtos.subject.SubjectCreateDTO;
import fr.inpt.frappe.controllers.dtos.subject.SubjectUpdateDTO;
import fr.inpt.frappe.models.Subject;
import fr.inpt.frappe.models.TeachingUnit;
import fr.inpt.frappe.repositories.TeachingUnitRepository;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class SubjectMapper {

	@Autowired
	protected TeachingUnitRepository teachingUnits;

	@Named("mapTeachingUnit")
	public Collection<TeachingUnit> mapTeachingUnit(Collection<Long> teaching_units) {
		if (teaching_units == null || teaching_units.isEmpty())
			return null;
		Collection<TeachingUnit> result = teachingUnits.findAllById(teaching_units);
		if (result.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ID(s) for teaching unit " + teaching_units);

		return result;
	}

	@Mapping(source = "teaching_units", target = "teachingUnits", qualifiedByName = "mapTeachingUnit")
	@Mapping(target = "documents", ignore = true)
	public abstract Subject createSubjectFromDTO(SubjectCreateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "teaching_units", target = "teachingUnits", qualifiedByName = "mapTeachingUnit")
	@Mapping(target = "documents", ignore = true)
	public abstract void updateSubjectFromDto(SubjectUpdateDTO dto, @MappingTarget Subject subject);
}
