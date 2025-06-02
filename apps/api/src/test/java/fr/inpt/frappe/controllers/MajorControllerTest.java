package fr.inpt.frappe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.inpt.frappe.controllers.dtos.major.MajorCreateDTO;
import fr.inpt.frappe.controllers.dtos.major.MajorUpdateDTO;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.repositories.MajorRepository;
import fr.inpt.frappe.repositories.SchoolRepository;

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
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MajorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MajorRepository majorRepository;

	@MockitoBean
	private SchoolRepository schoolRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Major major;
	private School school;

	private MajorCreateDTO majorCreateDTO;
	private MajorUpdateDTO majorUpdateDTO;

	@BeforeEach
	void setUp() {
		school = new School("n7", "ENSEEIHT");
		major = new Major("sdn", "Science du Numérique", school);
		majorCreateDTO = new MajorCreateDTO("sdn", "Science du Numérique", "n7", null);
		majorUpdateDTO = new MajorUpdateDTO("Science du numérique", "sdn", "n7", false);
	}

	@Test
	void testListMajors() throws Exception {
		when(majorRepository.findAll(ArgumentMatchers.<Specification<Major>>any())).thenReturn(Arrays.asList(major));
		mockMvc.perform(get("/major/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(major.getName()));
	}

	@Test
	void testCreateMajor() throws Exception {
		when(majorRepository.save(any(Major.class))).thenReturn(major);
		when(schoolRepository.findByUid(anyString())).thenReturn(Optional.of(school));
		mockMvc.perform(post("/major/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(majorCreateDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(major.getName()));
		when(majorRepository.save(any(Major.class))).thenReturn(major);
		when(schoolRepository.findByUid(anyString())).thenReturn(Optional.empty());
		mockMvc.perform(post("/major/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(majorCreateDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testReadMajor() throws Exception {
		when(majorRepository.findById(anyLong())).thenReturn(Optional.of(major));
		mockMvc.perform(get("/major/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(major.getName()));

		when(majorRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(get("/major/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateMajor() throws Exception {
		when(majorRepository.findById(anyLong())).thenReturn(Optional.of(major));
		when(schoolRepository.findByUid(any(String.class))).thenReturn(Optional.of(school));
		when(majorRepository.save(any(Major.class))).thenReturn(major);
		mockMvc.perform(patch("/major/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(majorUpdateDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(major.getName()));

		when(majorRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch("/major/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(major)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteMajor() throws Exception {
		when(majorRepository.findById(anyLong())).thenReturn(Optional.of(major));
		doNothing().when(majorRepository).delete(any(Major.class));
		mockMvc.perform(delete("/major/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		when(majorRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(delete("/major/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
