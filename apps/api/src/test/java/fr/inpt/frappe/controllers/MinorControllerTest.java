package fr.inpt.frappe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.inpt.frappe.controllers.dtos.MinorCreateDTO;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.repositories.MajorRepository;
import fr.inpt.frappe.repositories.MinorRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MinorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MinorRepository minorRepository;

	@MockitoBean
	private MajorRepository majorRepository;

	@MockitoBean
	private SchoolRepository schoolRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Minor minor;
	private Major major;
	private School school;

	private MinorCreateDTO minorCreateDTO;

	@BeforeEach
	void setUp() {
		school = new School("n7", "ENSEEIHT");
		major = new Major("sdn", "Science du Numérique", school);
		minor = new Minor("Archi système rézo", major);
		minorCreateDTO = new MinorCreateDTO("ASR", "sdn");
	}

	@Test
	void testListMinors() throws Exception {
		when(minorRepository.findAll()).thenReturn(Arrays.asList(minor));
		mockMvc.perform(get("/minor/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(minor.getName()));
	}

	@Test
	void testCreateMinor() throws Exception {
		when(minorRepository.save(any(Minor.class))).thenReturn(minor);
		when(majorRepository.findByUid(anyString())).thenReturn(Optional.of(major));
		mockMvc.perform(post("/minor/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(minorCreateDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(minor.getName()));
		when(minorRepository.save(any(Minor.class))).thenReturn(minor);
		when(majorRepository.findByUid(anyString())).thenReturn(Optional.empty());
		mockMvc.perform(post("/minor/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(minorCreateDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testReadMinor() throws Exception {
		when(minorRepository.findById(anyLong())).thenReturn(Optional.of(minor));
		mockMvc.perform(get("/minor/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(minor.getName()));

		when(minorRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(get("/minor/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateMinor() throws Exception {
		when(minorRepository.findById(anyLong())).thenReturn(Optional.of(minor));
		when(minorRepository.save(any(Minor.class))).thenReturn(minor);
		mockMvc.perform(patch("/minor/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("ASR"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("ASR"));

		when(minorRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch("/minor/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(minor)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteminor() throws Exception {
		when(minorRepository.findById(anyLong())).thenReturn(Optional.of(minor));
		doNothing().when(minorRepository).delete(any(Minor.class));
		mockMvc.perform(delete("/minor/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		when(minorRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(delete("/minor/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
