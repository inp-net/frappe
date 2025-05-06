package fr.inpt.frappe.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.inpt.frappe.controllers.dtos.TagDTO;
import fr.inpt.frappe.models.Tag;
import fr.inpt.frappe.repositories.TagRepository;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TagControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TagRepository tagRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Tag tag;
	private TagDTO tagDTO;

	@BeforeEach
	void setUp() {
		tag = new Tag("cours");

		tagDTO = new TagDTO("cours");
	}

	@Test
	void testListTags() throws Exception {
		when(tagRepository.findAll()).thenReturn(Arrays.asList(tag));
		mockMvc.perform(get("/tag/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(tag.getName()));
	}

	@Test
	void testCreateTag() throws Exception {
		when(tagRepository.save(any(Tag.class))).thenReturn(tag);
		mockMvc.perform(post("/tag/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tagDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(tag.getName()));
	}

	@Test
	void testReadTag() throws Exception {
		when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
		mockMvc.perform(get("/tag/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(tag.getName()));

		when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(get("/tag/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateTag() throws Exception {
		when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
		when(tagRepository.save(any(Tag.class))).thenReturn(tag);
		mockMvc.perform(patch("/tag/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tagDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(tag.getName()));

		when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch("/tag/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tagDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteTag() throws Exception {
		when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
		doNothing().when(tagRepository).delete(any(Tag.class));
		mockMvc.perform(delete("/tag/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(delete("/tag/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
