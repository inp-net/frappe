package fr.inpt.frappe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.inpt.frappe.controllers.dtos.subject.SubjectCreateDTO;
import fr.inpt.frappe.controllers.dtos.subject.SubjectUpdateDTO;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Subject;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.models.TeachingUnit;
import fr.inpt.frappe.repositories.MajorRepository;
import fr.inpt.frappe.repositories.SubjectRepository;
import fr.inpt.frappe.repositories.SchoolRepository;
import fr.inpt.frappe.repositories.TeachingUnitRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SubjectControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private SubjectRepository subjectRepository;

	@MockitoBean
	private TeachingUnitRepository teachingUnitRepository;

	@MockitoBean
	private MajorRepository majorRepository;

	@MockitoBean
	private SchoolRepository schoolRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Subject subject;
	private TeachingUnit teachingUnit;
	private Major major;
	private School school;

	private SubjectCreateDTO subjectCreateDTO;
	private SubjectUpdateDTO subjectUpdateDTO1;
	private SubjectUpdateDTO subjectUpdateDTO2;
	private SubjectUpdateDTO subjectUpdateDTO3;

	@BeforeEach
	void setUp() {
		school = new School("n7", "ENSEEIHT");
		major = new Major("sdn", "Science du Numérique", school);
		teachingUnit = new TeachingUnit("UE appli web", null, List.of(major));
		ArrayList<TeachingUnit> ltu = new ArrayList<>();
		ltu.add(teachingUnit);
		subject = new Subject("Application WEB", ltu);

		ArrayList<Long> ll = new ArrayList<>();
		ll.add(Long.valueOf(1));
		subjectCreateDTO = new SubjectCreateDTO(subject.getName(), ll);
		subjectUpdateDTO1 = new SubjectUpdateDTO();
		subjectUpdateDTO2 = new SubjectUpdateDTO();
		subjectUpdateDTO3 = new SubjectUpdateDTO();

		subjectUpdateDTO1.setName("Application WEEEEEB");
		subjectUpdateDTO1.setTeaching_units(null);
		subjectUpdateDTO2.setTeaching_units(ll);
		subjectUpdateDTO2.setName(null);

		ArrayList<Long> ll2 = new ArrayList<>();
		subjectUpdateDTO3.setTeaching_units(ll2);
		subjectUpdateDTO3.setName(null);
	}

	@Test
	void testListSubjects() throws Exception {
		when(subjectRepository.findAll(ArgumentMatchers.<Specification<Subject>>any())).thenReturn(Arrays.asList(subject));
		mockMvc.perform(get("/subject/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(subject.getName()));
	}

	@Test
	void testCreateSubject() throws Exception {
		when(subjectRepository.save(any(Subject.class))).thenReturn(subject);
		when(teachingUnitRepository.findAllById(anyCollection())).thenReturn(List.of(teachingUnit));
		mockMvc.perform(post("/subject/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(subjectCreateDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(subject.getName()));

		when(subjectRepository.save(any(Subject.class))).thenReturn(subject);
		when(teachingUnitRepository.findAllById(anyCollection())).thenReturn(List.of());
		mockMvc.perform(post("/subject/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(subjectCreateDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testReadSubject() throws Exception {
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		mockMvc.perform(get("/subject/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(subject.getName()));

		when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(get("/subject/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateSubject() throws Exception {
		// Test of changing name only
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		when(subjectRepository.save(any(Subject.class))).thenReturn(subject);
		mockMvc.perform(patch("/subject/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(subjectUpdateDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(subject.getName()));

		// test of changing teaching units only
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		when(subjectRepository.save(any(Subject.class))).thenReturn(subject);
		when(teachingUnitRepository.findAllById(anyList())).thenReturn(List.of(teachingUnit));
		mockMvc.perform(patch("/subject/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(subjectUpdateDTO2)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(subject.getName()));

		// test of changing teaching units when the ID is not in the db
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		when(subjectRepository.save(any(Subject.class))).thenReturn(subject);
		when(teachingUnitRepository.findAllById(anyList())).thenReturn(List.of());
		mockMvc.perform(patch("/subject/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(subjectUpdateDTO2)))
				.andExpect(status().isNotFound());

		// test of changing teaching units when the list of IDs is empty
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		when(subjectRepository.save(any(Subject.class))).thenReturn(subject);
		when(teachingUnitRepository.findAllById(anyList())).thenReturn(List.of());
		mockMvc.perform(patch("/subject/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(subjectUpdateDTO3)))
				.andExpect(status().isOk());

		// test of changing anything on a false subject id
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch("/subject/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(subjectUpdateDTO1)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeletesubject() throws Exception {
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		doNothing().when(subjectRepository).delete(any(Subject.class));
		mockMvc.perform(delete("/subject/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(delete("/subject/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
