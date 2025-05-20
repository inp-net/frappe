package fr.inpt.frappe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.inpt.frappe.controllers.dtos.document.DocumentCreateDTO;
import fr.inpt.frappe.controllers.dtos.document.DocumentUpdateDTO;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Document;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.models.Subject;
import fr.inpt.frappe.models.Tag;
import fr.inpt.frappe.models.TeachingUnit;
import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.DocumentRepository;
import fr.inpt.frappe.repositories.FileRepository;
import fr.inpt.frappe.repositories.MajorRepository;
import fr.inpt.frappe.repositories.SubjectRepository;
import fr.inpt.frappe.repositories.TagRepository;
import fr.inpt.frappe.repositories.SchoolRepository;
import fr.inpt.frappe.repositories.UserRepository;

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
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DocumentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DocumentRepository documentRepository;

	@MockitoBean
	private FileRepository fileRepository;

	@MockitoBean
	private SubjectRepository subjectRepository;

	@MockitoBean
	private UserRepository userRepository;

	@MockitoBean
	private TagRepository tagRepository;

	@MockitoBean
	private MajorRepository majorRepository;

	@MockitoBean
	private SchoolRepository schoolRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Document document;
	private Document documentM;
	private Subject subject;
	private TeachingUnit teachingUnit;
	private Major major;
	private School school;
	private Tag tag;
	private User user;

	private DocumentCreateDTO documentCreateDTO;
	private DocumentUpdateDTO documentUpdateDTO1;
	private DocumentUpdateDTO documentUpdateDTO2;

	@BeforeEach
	void setUp() {
		user = new User("JC", "Jacques", "Célere", 1);
		school = new School("n7", "ENSEEIHT");
		major = new Major("sdn", "Science du Numérique", school);
		teachingUnit = new TeachingUnit("UE appli web", null, List.of(major));
		ArrayList<TeachingUnit> ltu = new ArrayList<>();
		ltu.add(teachingUnit);
		subject = new Subject("Application WEB", ltu);
		document = new Document("partiel 1", "partiel d'application web", subject);
		documentM = new Document("partiel 2", "partiel d'Application Web", subject);

		ArrayList<Document> docList = new ArrayList<>();
		docList.add(document);
		docList.add(documentM);

		tag = new Tag("partiel");

		tag.setDocuments(docList);

		ArrayList<Long> tagList = new ArrayList<>();
		tagList.add(Long.valueOf(1));

		documentCreateDTO = new DocumentCreateDTO("partiel 1", "partiel d'application web", Long.valueOf(1),
				UUID.fromString("49e561d4-58f2-477b-bd18-4e603f81d2cb"), tagList);

		documentUpdateDTO1 = new DocumentUpdateDTO("partiel 2", "partiel d'Application Web",
				UUID.fromString("49e561d4-58f2-477b-bd18-4e603f81d2cb"), tagList);
		documentUpdateDTO2 = new DocumentUpdateDTO(null, null,
				null, tagList);
	}

	@Test
	void testListDocuments() throws Exception {
		when(documentRepository.findAll(ArgumentMatchers.<Specification<Document>>any()))
				.thenReturn(Arrays.asList(document));
		mockMvc.perform(get("/document/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value(document.getTitle()));

		when(documentRepository.findAll(ArgumentMatchers.<Specification<Document>>any()))
				.thenReturn(Arrays.asList(document, documentM));
		mockMvc.perform(get("/document/?tagIDs=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value(document.getTitle()))
				.andExpect(jsonPath("$[1].title").value(documentM.getTitle()));
	}

	@Test
	void testCreateDocument() throws Exception {
		when(documentRepository.save(any(Document.class))).thenReturn(document);
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
		mockMvc.perform(post("/document/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(documentCreateDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value(document.getTitle()));

		when(documentRepository.save(any(Document.class))).thenReturn(document);
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
		mockMvc.perform(post("/document/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(documentCreateDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value(document.getTitle()));

		when(documentRepository.save(any(Document.class))).thenReturn(document);
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
		mockMvc.perform(post("/document/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(documentCreateDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testReadDocument() throws Exception {
		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.of(document));
		mockMvc.perform(get("/document/71ea8ba9-7cda-47e9-ac81-e835f54486ba")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(document.getTitle()));

		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		mockMvc.perform(get("/document/71ea8ba9-7cda-47e9-ac81-e835f54486ba")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateDocument() throws Exception {
		// Test of changing title only
		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.of(document));
		when(documentRepository.save(any(Document.class))).thenReturn(documentM);
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
		mockMvc.perform(patch("/document/71ea8ba9-7cda-47e9-ac81-e835f54486ba")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(documentUpdateDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(documentM.getTitle()));

		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.of(document));
		when(documentRepository.save(any(Document.class))).thenReturn(documentM);
		when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
		mockMvc.perform(patch("/document/71ea8ba9-7cda-47e9-ac81-e835f54486ba")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(documentUpdateDTO2)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(documentM.getTitle()));

		// test of changing anything on a false document id
		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
		when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
		mockMvc.perform(patch("/document/71ea8ba9-7cda-47e9-ac81-e835f54486ba")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(documentUpdateDTO1)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeletesubject() throws Exception {
		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.of(document));
		doNothing().when(documentRepository).delete(any(Document.class));
		mockMvc.perform(delete("/document/71ea8ba9-7cda-47e9-ac81-e835f54486ba")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		when(documentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		mockMvc.perform(delete("/document/71ea8ba9-7cda-47e9-ac81-e835f54486ba")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
