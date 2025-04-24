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
import fr.inpt.frappe.controllers.dtos.teaching_units.TeachingUnitCreateDTO;
import fr.inpt.frappe.controllers.dtos.teaching_units.TeachingUnitUpdateDTO;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.TeachingUnit;
import fr.inpt.frappe.repositories.MajorRepository;
import fr.inpt.frappe.repositories.MinorRepository;
import fr.inpt.frappe.repositories.SubjectRepository;

@AnnotateWith(GeneratedMapper.class)
@Mapper(componentModel = "spring")
public abstract class TeachingUnitMapper {

	@Autowired
	protected MajorRepository majorRepository;

	@Autowired
	protected MinorRepository minorRepository;

	@Autowired
	protected SubjectRepository subjectRepository;

	@Named("mapMajors")
	public Collection<Major> mapMajors(Collection<Long> majors_id) {
		if (majors_id == null || majors_id.isEmpty())
			return null;
		Collection<Major> results = majorRepository.findAllById(majors_id);
		if (results.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ID(s) for major(s) " + majors_id);
		return results;
	}

	@Named("mapMinors")
	public Collection<Minor> mapMinors(Collection<Long> minors_id) {
		if (minors_id == null || minors_id.isEmpty())
			return null;
		Collection<Minor> results = minorRepository.findAllById(minors_id);
		if (results.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ID(s) for minor(s) " + minors_id);
		return results;
	}

	@Mapping(source = "majors", target = "majors", qualifiedByName = "mapMajors")
	@Mapping(source = "minors", target = "minors", qualifiedByName = "mapMinors")
	@Mapping(target = "subjects", ignore = true)
	public abstract TeachingUnit createTeachingUnitFromDto(TeachingUnitCreateDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "majors", target = "majors", qualifiedByName = "mapMajors")
	@Mapping(source = "minors", target = "minors", qualifiedByName = "mapMinors")
	@Mapping(target = "subjects", ignore = true)
	public abstract TeachingUnit updateTeachingUnitFromDto(TeachingUnitUpdateDTO dto,
			@MappingTarget TeachingUnit teachingUnit);
}
