package fr.inpt.frappe.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.inpt.frappe.utils;
import fr.inpt.frappe.auth.AuthUser;
import fr.inpt.frappe.auth.JwtProvider;
import fr.inpt.frappe.controllers.dtos.FileDTO;
import fr.inpt.frappe.controllers.dtos.document.DocumentCreateDTO;
import fr.inpt.frappe.controllers.dtos.document.DocumentUpdateDTO;
import fr.inpt.frappe.mappers.DocumentMapper;
import fr.inpt.frappe.mappers.FileMapper;
import fr.inpt.frappe.models.Document;
import fr.inpt.frappe.models.specification.DocumentSpecification;
import fr.inpt.frappe.models.File;
import fr.inpt.frappe.repositories.DocumentRepository;
import fr.inpt.frappe.repositories.FileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/document")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Document", description = "Manage documents")
public class DocumentController {

	@Autowired
	private DocumentRepository documents;

	@Autowired
	private FileRepository files;

	@Autowired
	private DocumentMapper mapper;

	@Autowired
	private FileMapper fileMapper;

	private Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	private final Tika tika = new Tika();

	// Upload path
	private String basePath = Paths.get("")
			.toAbsolutePath() // e.g., /path/to/frappe/apps/api
			.getParent() // -> /path/to/frappe/apps
			.getParent() // -> /path/to/frappe
			.resolve("uploads") // -> /path/to/frappe/uploads
			.toString();

	@Operation(summary = "Get all documents", description = "Returns a list of documents that has one of the desired tag (if there is no tags return all the documents).")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of documents"),
			@ApiResponse(responseCode = "404", description = "Tag ID not found")
	})
	@GetMapping("/")
	public Collection<Document> list(@RequestParam(required = false) List<Long> tagIds,
			@RequestParam(required = false) Long schoolId,
			@RequestParam(required = false) Long majorId,
			@RequestParam(required = false) Long minorId,
			@RequestParam(required = false) List<Long> teachingUnitIds,
			@RequestParam(required = false) List<Long> subjectIds) {

		Specification<Document> spec = Specification.where(DocumentSpecification.hasTags(tagIds))
				.and(DocumentSpecification.hasSubjects(subjectIds))
				.and(DocumentSpecification.hasTeachingUnits(teachingUnitIds))
				.and(DocumentSpecification.hasMinor(minorId))
				.and(DocumentSpecification.hasMajor(majorId))
				.and(DocumentSpecification.hasSchool(schoolId));

		return documents.findAll(spec);
	}

	@Operation(summary = "Create a new document", description = "Creates and returns a new document.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Document created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
	})
	@PostMapping("/")
	public ResponseEntity<Document> create(
			@AuthenticationPrincipal AuthUser principal,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The document") @Valid @RequestBody DocumentCreateDTO document) {

		if (document.getAuthor() == null)
			document.setAuthor(principal.getUser().getId());

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(documents.save(mapper.createDocumentFromDto(document)));
	}

	@PostMapping(path = "/{id}/upload", consumes = "multipart/form-data")
	@Operation(summary = "Create a new File and store it", description = "Creates and returns a new file.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "File created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
	})
	public ResponseEntity<File> uploadFile(
			@PathVariable UUID id,
			@Parameter(description = "File to upload", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(type = "string", format = "binary"))) @RequestPart("file") MultipartFile file) {

		File createdFile = new File();
		String mimeType = "";
		try {
			mimeType = tika.detect(file.getInputStream());

		} catch (IOException e) {
			logger.error("Could not read the file type : \n" + e.getMessage());
		}

		String extension = utils.getExtension(mimeType);

		if (extension == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "filetype is not allowed");

		String cleanedName = FilenameUtils.removeExtension(utils.sanitize(file.getOriginalFilename()));

		// Save the file to the DB
		createdFile = files.save(fileMapper
				.createFileFromDto(new FileDTO(cleanedName, extension, id)));

		// Make sure the directory exists
		java.io.File uploadDir = new java.io.File(basePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				basePath + "/" + createdFile.getId().toString())) {
			fileOutputStream.write(file.getBytes());

		} catch (IOException e) {
			logger.error("Error in saving the file to the server : \n" + e.toString());
		}

		logger.debug(
				"Saving " + cleanedName + " of type: " + mimeType + " as " + createdFile.getId().toString()
						+ extension);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createdFile);
	}

	@Operation(summary = "Get the specified file", description = "Get the link to download the specified file.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Link created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid file ID", content = @Content),
			@ApiResponse(responseCode = "404", description = "File not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Malformated url to get the ressource", content = @Content)
	})
	@GetMapping("/file/{fileID}")
	public ResponseEntity<UrlResource> getFile(@PathVariable UUID fileID) {
		try {
			File file = files.findById(fileID).orElseThrow(() -> new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"File not found"));
			Path filePath = Paths.get(basePath).resolve(fileID.toString());
			UrlResource resource = new UrlResource(filePath.toUri());

			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_OCTET_STREAM)
						.header(HttpHeaders.CONTENT_DISPOSITION,
								"inline; filename=\"" + file.getName() + file.getExtension() + "\"") // Put the original
																										// name for the
						// download
						.body(resource);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"File not found and may does not exist anymore");
			}
		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Malformated url to get the file");
		}
	}

	@Operation(summary = "Get a document by ID", description = "Retrieves a document by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Document retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
	})
	@GetMapping("/{id}")
	public Document read(@PathVariable UUID id) {
		return documents.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Document not found"));
	}

	@PatchMapping(path = "/file/{fileID}")
	@Operation(summary = "Rename a file", description = "Update the specified file name.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "File name updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "File not found", content = @Content)
	})
	public ResponseEntity<File> renameFile(
			@PathVariable UUID fileID,
			@Parameter(description = "New file name") String name) {

		File file = files.findById(fileID).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"File not found"));

		file.setName(FilenameUtils.removeExtension(utils.sanitize(name)));

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(files.save(file));
	}

	@Operation(summary = "Update a document", description = "Updates the desired document.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Document updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public Document update(
			@PathVariable UUID id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated document") @Valid @RequestBody DocumentUpdateDTO documentUpdate) {

		Document document = documents.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

		mapper.updateDocumentFromDto(documentUpdate, document);

		return documents.save(document);
	}

	@Operation(summary = "Delete a document", description = "Deletes the desired document.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Document deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		Document document = documents.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

		Collection<File> files = document.getFiles();
		if (files != null)
			files.forEach(file -> utils.removeFile(basePath, file));

		documents.delete(document);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/file/{fileID}")
	@Operation(summary = "Delete a file", description = "Deletes the desired file.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "File deleted successfully"),
			@ApiResponse(responseCode = "404", description = "File not found", content = @Content)
	})
	public ResponseEntity<Void> deleteFile(@PathVariable UUID fileID) {

		File oldFile = files.findById(fileID).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"File not found"));

		utils.removeFile(basePath, oldFile);

		files.delete(oldFile);

		return ResponseEntity.noContent().build();
	}
}
