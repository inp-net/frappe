package fr.inpt.frappe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.inpt.frappe.controllers.dtos.school.SchoolCreateDTO;
import fr.inpt.frappe.controllers.dtos.school.SchoolUpdateDTO;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.repositories.SchoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SchoolControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private SchoolRepository schoolRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private School school;
	private SchoolUpdateDTO schoolUpdateDTO;
	private SchoolCreateDTO schoolCreateDTO;

	@BeforeEach
	void setUp() {
		school = new School("n7", "ENSEEIHT");

		schoolCreateDTO = new SchoolCreateDTO("n7", "ENSEEIHT");
		schoolUpdateDTO = new SchoolUpdateDTO("ENSEEIHT");

	}

	@Test
	void testListSchools() throws Exception {
		when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));
		mockMvc.perform(get("/school/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(school.getName()));
	}

	@Test
	void testCreateSchool() throws Exception {
		when(schoolRepository.save(any(School.class))).thenReturn(school);
		mockMvc.perform(post("/school/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(schoolCreateDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(school.getName()));
	}

	@Test
	void testReadSchool() throws Exception {
		when(schoolRepository.findById(anyLong())).thenReturn(Optional.of(school));
		mockMvc.perform(get("/school/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(school.getName()));

		when(schoolRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(get("/school/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateSchool() throws Exception {
		when(schoolRepository.findById(anyLong())).thenReturn(Optional.of(school));
		when(schoolRepository.save(any(School.class))).thenReturn(school);
		mockMvc.perform(patch("/school/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(schoolUpdateDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(school.getName()));

		when(schoolRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch("/school/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(schoolUpdateDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteSchool() throws Exception {
		when(schoolRepository.findById(anyLong())).thenReturn(Optional.of(school));
		doNothing().when(schoolRepository).delete(any(School.class));
		mockMvc.perform(delete("/school/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		when(schoolRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(delete("/school/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
