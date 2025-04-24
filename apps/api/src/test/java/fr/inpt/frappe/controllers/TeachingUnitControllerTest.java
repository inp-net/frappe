package fr.inpt.frappe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.inpt.frappe.controllers.dtos.teaching_units.TeachingUnitCreateDTO;
import fr.inpt.frappe.controllers.dtos.teaching_units.TeachingUnitUpdateDTO;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.models.TeachingUnit;
import fr.inpt.frappe.repositories.MajorRepository;
import fr.inpt.frappe.repositories.MinorRepository;
import fr.inpt.frappe.repositories.TeachingUnitRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TeachingUnitControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TeachingUnitRepository teachingUnitRepository;

	@MockitoBean
	private MajorRepository majorRepository;

	@MockitoBean
	private MinorRepository minorRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private School school;
	private TeachingUnit teachingUnit1;
	private TeachingUnit teachingUnit2;
	private Major major;
	private Collection<Major> majors;
	private Collection<Long> majors_id;
	private Minor minor1;
	private Minor minor2;
	private Collection<Minor> minors;
	private Collection<Long> minors_id;

	private TeachingUnitCreateDTO teachingUnitCreateDTO1;
	private TeachingUnitCreateDTO teachingUnitCreateDTO2;
	private TeachingUnitCreateDTO teachingUnitCreateDTO3;
	private TeachingUnitCreateDTO teachingUnitCreateDTO4;

	private TeachingUnitUpdateDTO teachingUnitUpdateDTO1;
	private TeachingUnitUpdateDTO teachingUnitUpdateDTO2;

	@BeforeEach
	void setUp() {
		school = new School("n7", "ENSEEIHT");

		major = new Major("sdn", "Science du Numérique", school);
		majors = new ArrayList<Major>();
		majors.add(major);
		majors_id = new ArrayList<Long>();
		majors_id.add(major.getId());

		minor1 = new Minor("BigData", major);
		minor2 = new Minor("IMM", major);
		minors = new ArrayList<Minor>();
		minors.add(minor1);
		minors.add(minor2);
		minors_id = new ArrayList<Long>();
		minors_id.add(minor1.getId());
		minors_id.add(minor2.getId());

		teachingUnit1 = new TeachingUnit("Modélisation Géométrique", minors, null);
		teachingUnit2 = new TeachingUnit("PIM", null, majors);

		teachingUnitCreateDTO1 = new TeachingUnitCreateDTO(teachingUnit1.getName(), null, minors_id);
		teachingUnitCreateDTO2 = new TeachingUnitCreateDTO(teachingUnit2.getName(), majors_id, null);
		teachingUnitCreateDTO3 = new TeachingUnitCreateDTO(teachingUnit1.getName(), null, new ArrayList<Long>());
		teachingUnitCreateDTO4 = new TeachingUnitCreateDTO(teachingUnit1.getName(), new ArrayList<Long>(), null);

		teachingUnitUpdateDTO1 = new TeachingUnitUpdateDTO(teachingUnit1.getName(), majors_id, null);
		teachingUnitUpdateDTO2 = new TeachingUnitUpdateDTO(teachingUnit2.getName(), null, new ArrayList<Long>());
	}

	@Test
	void testListTeachingUnit() throws Exception {
		when(teachingUnitRepository.findAll()).thenReturn(Arrays.asList(teachingUnit1));
		mockMvc.perform(get("/teachingunit/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(teachingUnit1.getName()));
	}

	@Test
	void testCreateTeachingUnit() throws Exception {
		when(teachingUnitRepository.save(any(TeachingUnit.class))).thenReturn(teachingUnit1);
		when(minorRepository.findAllById(anyCollection())).thenReturn(List.of(minor1));
		mockMvc.perform(post("/teachingunit/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitCreateDTO1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(teachingUnit1.getName()));

		when(majorRepository.findAllById(anyCollection())).thenReturn(List.of(major));
		mockMvc.perform(post("/teachingunit/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitCreateDTO2)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(teachingUnit2.getName()));

		when(minorRepository.findAllById(anyCollection())).thenReturn(new ArrayList<Minor>());
		mockMvc.perform(post("/teachingunit/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitCreateDTO1)))
				.andExpect(status().isNotFound());

		when(majorRepository.findAllById(anyCollection())).thenReturn(new ArrayList<Major>());
		mockMvc.perform(post("/teachingunit/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitCreateDTO2)))
				.andExpect(status().isNotFound());

		mockMvc.perform(post("/teachingunit/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitCreateDTO3)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(teachingUnit1.getName()));

		mockMvc.perform(post("/teachingunit/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitCreateDTO4)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(teachingUnit1.getName()));
	}

	@Test
	void testReadTeachingUnit() throws Exception {
		when(teachingUnitRepository.findById(anyLong())).thenReturn(Optional.of(teachingUnit1));
		mockMvc.perform(get("/teachingunit/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(teachingUnit1.getName()));

		when(teachingUnitRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(get("/teachingunit/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateTeachingUnit() throws Exception {
		when(teachingUnitRepository.findById(anyLong())).thenReturn(Optional.of(teachingUnit1));
		when(teachingUnitRepository.save(any(TeachingUnit.class))).thenReturn(teachingUnit1);
		when(majorRepository.findAllById(anyCollection())).thenReturn(List.of(major));
		mockMvc.perform(patch("/teachingunit/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitUpdateDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(teachingUnit1.getName()));

		when(teachingUnitRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch("/teachingunit/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitUpdateDTO1)))
				.andExpect(status().isNotFound());

		when(teachingUnitRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch("/teachingunit/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teachingUnitUpdateDTO2)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteTeachingUnit() throws Exception {
		when(teachingUnitRepository.findById(anyLong())).thenReturn(Optional.of(teachingUnit1));
		doNothing().when(teachingUnitRepository).delete(any(TeachingUnit.class));
		mockMvc.perform(delete("/teachingunit/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		when(teachingUnitRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(delete("/teachingunit/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
