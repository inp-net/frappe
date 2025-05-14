package fr.inpt.frappe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.inpt.frappe.controllers.dtos.comment.CommentCreateDTO;
import fr.inpt.frappe.controllers.dtos.comment.CommentUpdateDTO;
import fr.inpt.frappe.models.Comment;
import fr.inpt.frappe.models.Document;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.models.Subject;
import fr.inpt.frappe.models.TeachingUnit;
import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.CommentRepository;
import fr.inpt.frappe.repositories.DocumentRepository;
import fr.inpt.frappe.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CommentRepository commentRepository;

	@MockitoBean
	private DocumentRepository documentRepository;

	@MockitoBean
	private UserRepository userRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private User user;
	private School school;
	private Major major;
	private TeachingUnit teachingUnit;
	private Subject subject;
	private Document document;
	private Comment comment;

	private CommentCreateDTO commentCreateDTO;
	private CommentCreateDTO commentCreateDTO2;
	private CommentUpdateDTO commentUpdateDTO;

	@BeforeEach
	void setUp() {

		user = new User("dupondd", "dupont", "dupond", 1);
		school = new School("n7", "ENSEEIHT");
		major = new Major("sdn", "Science du Numérique", school);
		teachingUnit = new TeachingUnit("Architecture des systèmes d'exploitation", null, List.of(major));
		subject = new Subject("Archi système", List.of(teachingUnit));
		document = new Document("un partiel", subject);
		comment = new Comment("En effet ce partiel est trop cool", document, user);

		commentCreateDTO = new CommentCreateDTO(comment.getContent(), UUID.randomUUID(), UUID.randomUUID());
		commentCreateDTO2 = new CommentCreateDTO();
		commentCreateDTO2.setContent(comment.getContent());
		commentCreateDTO2.setDocument(UUID.randomUUID());
		commentUpdateDTO = new CommentUpdateDTO("En effet ce partiel est trop cool");

	}

	@Test
	void testListComments() throws Exception {
		when(commentRepository.findAll()).thenReturn(Arrays.asList(comment));
		mockMvc.perform(get("/comment/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].content").value(comment.getContent()));
	}

	@Test
	void testCreateComment() throws Exception {
		when(commentRepository.save(any(Comment.class))).thenReturn(comment);
		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.of(document));
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
		mockMvc.perform(post("/comment/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(commentCreateDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.content").value(comment.getContent()));

		when(commentRepository.save(any(Comment.class))).thenReturn(comment);
		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		mockMvc.perform(post("/comment/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(commentCreateDTO)))
				.andExpect(status().isNotFound());

		when(commentRepository.save(any(Comment.class))).thenReturn(comment);
		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.of(document));
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		mockMvc.perform(post("/comment/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(commentCreateDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testReadComment() throws Exception {
		when(commentRepository.findById(any(UUID.class))).thenReturn(Optional.of(comment));
		mockMvc.perform(get("/comment/4b0788ed-e6ac-4f4d-9f98-f1ed1bb257de")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value(comment.getContent()));

		when(commentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		mockMvc.perform(get("/comment/4b0788ed-e6ac-4f4d-9f98-f1ed1bb257de")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateComment() throws Exception {
		when(commentRepository.findById(any(UUID.class))).thenReturn(Optional.of(comment));
		when(commentRepository.save(any(Comment.class))).thenReturn(comment);
		mockMvc.perform(patch("/comment/4b0788ed-e6ac-4f4d-9f98-f1ed1bb257de")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(commentUpdateDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value(comment.getContent()));

		when(commentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		mockMvc.perform(patch("/comment/4b0788ed-e6ac-4f4d-9f98-f1ed1bb257de")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(commentUpdateDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeletecomment() throws Exception {
		when(commentRepository.findById(any(UUID.class))).thenReturn(Optional.of(comment));
		doNothing().when(commentRepository).delete(any(Comment.class));
		mockMvc.perform(delete("/comment/4b0788ed-e6ac-4f4d-9f98-f1ed1bb257de")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		when(commentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		mockMvc.perform(delete("/comment/4b0788ed-e6ac-4f4d-9f98-f1ed1bb257de")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
