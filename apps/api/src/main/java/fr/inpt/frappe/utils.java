package fr.inpt.frappe;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.inpt.frappe.models.File;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.repositories.SchoolRepository;

public class utils {
	/**
	 * Parse the yearTier object present in the OIDC token into an integer.
	 * 
	 * @param yearTierObj The object to parse
	 * @return The parsed integer
	 */
	public static int parseYearTier(Object yearTierObj) {
		int yearTier = 0;

		if (yearTierObj != null) {
			if (yearTierObj instanceof String) {
				yearTier = Integer.parseInt((String) yearTierObj);
			} else if (yearTierObj instanceof Number) {
				yearTier = ((Number) yearTierObj).intValue();
			} else if (yearTierObj instanceof char[]) {
				yearTier = Integer.parseInt(new String((char[]) yearTierObj));
			} else {
				throw new IllegalArgumentException(
						"Unexpected type for yearTier: " + yearTierObj.getClass());
			}
		}

		return yearTier;
	}

	/**
	 * Get the schools from the major object present in the OIDC token.
	 * 
	 * @param major Major object
	 * @return The list of schools if found, empty otherwise
	 */
	public static List<String> getSchoolsFromMajor(Map<String, Object> major) {
		if (major == null)
			return new ArrayList<>();

		// Type checking is omitted here because we assume that if data exists,
		// it is correctly formatted and validated by authentik / churros.
		@SuppressWarnings("unchecked")
		List<Map<String, String>> schools = (List<Map<String, String>>) major.get("schools");
		return schools.stream()
				.map(school -> school.get("uid"))
				.collect(Collectors.toList());
	}

	/**
	 * Get the major from the major object present in the OIDC token.
	 * 
	 * @param major Major object
	 * @return The major if found, empty otherwise
	 */
	public static Optional<String> getMajorFromMajor(Map<String, Object> major) {
		if (major == null)
			return Optional.empty();

		return Optional.of((String) major.get("uid"));
	}

	/**
	 * Find the first school in the list of schools that is present in the database.
	 * 
	 * @param oidcSchools List of schools from the OIDC token
	 * @param schools     Repository of schools
	 * @return The school if found, empty otherwise
	 */
	public static Optional<School> findSchool(List<String> oidcSchools, SchoolRepository schools) {
		for (String oidcSchool : oidcSchools) {
			Optional<School> school = schools.findByUid(oidcSchool);
			return school;
		}

		return Optional.empty();
	}

	/**
	 * Return the file extension according to the myme type if the type of file is
	 * allowed
	 * 
	 * @Param mimeType the type to match
	 * @return The extension matching the mimeType, null if it is not allowed
	 */
	public static String getExtension(String mimeType) {

		String extension;

		// Check the autorized filetype
		switch (mimeType) {
			case "application/pdf":
				extension = ".pdf";
				break;
			case "image/jpeg":
				extension = ".jpg";
				break;
			case "image/bmp":
				extension = ".bmp";
				break;
			case "image/vnd.microsoft.icon":
				extension = ".ico";
				break;
			case "image/gif":
				extension = ".gif";
				break;
			case "image/png":
				extension = ".png";
				break;
			case "image/tiff":
				extension = ".tiff";
				break;
			case "image/svg+xml":
				extension = ".svg";
				break;
			default:
				extension = null;
		}

		return extension;
	}

	/**
	 * Sanitize filename
	 * 
	 * @Param fileName the name of the file
	 * @return the name of the file sanitized
	 */
	public static String sanitize(String fileName) {

		// Sanitize filename
		// Remove path parts
		String cleaned = Paths.get(fileName).getFileName().toString();

		// Remove illegal characters and control characters
		cleaned = cleaned.replaceAll("[^a-zA-Z0-9\\.\\-\\_éèàç]", "_");

		return cleaned;
	}

	/**
	 * Remove a file from the file system
	 * 
	 * @param basePath the path to the file system
	 * @param file te file to be removed
	 */
	public static void removeFile(String basePath, File file) {

		new java.io.File(
				basePath + "/" + file.getId().toString()).delete();
	}
}
