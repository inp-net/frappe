package fr.inpt.frappe.controllers;

import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.MinorRepository;
import fr.inpt.frappe.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserRepository userRepository;

	@MockitoBean
	private MinorRepository minorRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private User user;
	private Minor minor;
	private Major major;
	private School school;

	@BeforeEach
	void setUp() {
		user = new User("pointeauq", "quentin", "pointeau", 2026);
		school = new School("n7", "ENSEEIHT");
		major = new Major("sdn", "Science du Numérique", school);
		minor = new Minor("Archi système rézo", major);
	}

	@Test
	void testListUsers() throws Exception {
		when(userRepository.findAll()).thenReturn(Arrays.asList(user));
		mockMvc.perform(get("/user/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].uid").value(user.getUid()))
				.andExpect(jsonPath("$[0].firstname").value(user.getFirstname()))
				.andExpect(jsonPath("$[0].lastname").value(user.getLastname()))
				.andExpect(jsonPath("$[0].year").value(user.getYear()));
	}

	@Test
	void testReadUser() throws Exception {
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
		mockMvc.perform(get("/user/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.uid").value(user.getUid()))
				.andExpect(jsonPath("$.firstname").value(user.getFirstname()))
				.andExpect(jsonPath("$.lastname").value(user.getLastname()))
				.andExpect(jsonPath("$.year").value(user.getYear()));

		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		mockMvc.perform(get("/user/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateUser() throws Exception {
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
		when(minorRepository.findById(anyLong())).thenReturn(Optional.of(minor));
		when(userRepository.save(any(User.class))).thenReturn(user);
		mockMvc.perform(patch("/user/" + UUID.randomUUID() + "?minor_id=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname").value(user.getFirstname()));

		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
		when(minorRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch("/user/" + UUID.randomUUID() + "?minor_id=1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(minor)))
				.andExpect(status().isNotFound());
	}
}
