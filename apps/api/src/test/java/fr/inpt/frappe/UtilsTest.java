package fr.inpt.frappe;

import fr.inpt.frappe.models.School;
import fr.inpt.frappe.repositories.SchoolRepository;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilsTest {

	@Test
	void testUtils() {
		utils testUtils = new utils();
		assertNotNull(testUtils);
	}

	@Test
	void testParseYearTierString() {
		// Test case when yearTierObj is a String
		Object yearTierObj = "3";
		int result = utils.parseYearTier(yearTierObj);
		assertEquals(3, result);
	}

	@Test
	void testParseYearTierNumber() {
		// Test case when yearTierObj is a Number
		Object yearTierObj = 4;
		int result = utils.parseYearTier(yearTierObj);
		assertEquals(4, result);
	}

	@Test
	void testParseYearTierCharArray() {
		// Test case when yearTierObj is a char array
		Object yearTierObj = new char[] { '5' };
		int result = utils.parseYearTier(yearTierObj);
		assertEquals(5, result);
	}

	@Test
	void testParseYearTierNull() {
		// Test case when yearTierObj is null
		Object yearTierObj = null;
		int result = utils.parseYearTier(yearTierObj);
		assertEquals(0, result); // Defaults to 0 as per the implementation
	}

	@Test
	void testParseYearTierIllegalArgument() {
		// Test case when yearTierObj is an unsupported type
		Object yearTierObj = new Object();
		assertThrows(IllegalArgumentException.class, () -> utils.parseYearTier(yearTierObj));
	}

	@Test
	void testGetSchoolsFromMajor() {
		// Test case when major contains schools
		Map<String, Object> major = new HashMap<>();
		List<Map<String, String>> schools = new ArrayList<>();
		Map<String, String> school = new HashMap<>();
		school.put("uid", "n7");
		schools.add(school);
		major.put("schools", schools);

		List<String> result = utils.getSchoolsFromMajor(major);
		assertEquals(1, result.size());
		assertEquals("n7", result.get(0));
	}

	@Test
	void testGetSchoolsFromMajorEmpty() {
		// Test case when major contains no schools
		Map<String, Object> major = new HashMap<>();
		major.put("schools", new ArrayList<>());

		List<String> result = utils.getSchoolsFromMajor(major);
		assertTrue(result.isEmpty());
	}

	@Test
	void testGetSchoolsFromMajorNull() {
		// Test case when major is null
		List<String> result = utils.getSchoolsFromMajor(null);
		assertTrue(result.isEmpty());
	}

	@Test
	void testGetMajorFromMajor() {
		// Test case when major contains a valid uid
		Map<String, Object> major = new HashMap<>();
		major.put("uid", "n7");

		Optional<String> result = utils.getMajorFromMajor(major);
		assertTrue(result.isPresent());
		assertEquals("n7", result.get());
	}

	@Test
	void testGetMajorFromMajorNull() {
		// Test case when major is null
		Optional<String> result = utils.getMajorFromMajor(null);
		assertFalse(result.isPresent());
	}

	@Test
	void testFindSchoolFound() {
		// Mock the SchoolRepository to return a school when a school UID is found
		SchoolRepository schoolRepository = mock(SchoolRepository.class);
		School school = new School("n7", "ENSEEIHT");
		when(schoolRepository.findByUid("n7")).thenReturn(Optional.of(school));

		List<String> oidcSchools = Arrays.asList("n7");
		Optional<School> result = utils.findSchool(oidcSchools, schoolRepository);
		assertTrue(result.isPresent());
		assertEquals("n7", result.get().getUid());
	}

	@Test
	void testFindSchoolNotFound() {
		// Mock the SchoolRepository to return an empty Optional when no school is found
		SchoolRepository schoolRepository = mock(SchoolRepository.class);
		when(schoolRepository.findByUid("n7")).thenReturn(Optional.empty());

		List<String> oidcSchools = Arrays.asList("n7");
		Optional<School> result = utils.findSchool(oidcSchools, schoolRepository);
		assertFalse(result.isPresent());
	}

	@Test
	void testFindSchoolEmptyList() {
		// Test case when the list of OIDC schools is empty
		SchoolRepository schoolRepository = mock(SchoolRepository.class);
		List<String> oidcSchools = new ArrayList<>();
		Optional<School> result = utils.findSchool(oidcSchools, schoolRepository);
		assertFalse(result.isPresent());
	}

	@Test
	void testGetExtension() {
		assert (utils.getExtension("application/pdf").equals(".pdf"));
		assert (utils.getExtension("image/jpeg") == null);
	}

	@Test
	void testSanitizePath() {
		// Test path traversal robustness
		assert (utils.sanitize("/cool/path/traversal/file.pdf").equals("file.pdf"));
		assert (utils.sanitize("/../../../file.pdf").equals("file.pdf"));
		assert (utils.sanitize("/file.pdf").equals("file.pdf"));

		// Test removing illegal caracter
		assert (utils.sanitize("azertyuiopmlkjhgfdsqwxcvbn1234567890AZERTYUIOPMLKJHGFDSQWXCVBN-._.pdf")
				.equals("azertyuiopmlkjhgfdsqwxcvbn1234567890AZERTYUIOPMLKJHGFDSQWXCVBN-._.pdf"));
		assert (utils.sanitize("\\.pdf").equals("_.pdf"));
		assert (utils.sanitize("\".pdf").equals("_.pdf"));
		assert (utils.sanitize("&.pdf").equals("_.pdf"));
		assert (utils.sanitize("/.pdf").equals(".pdf"));
		assert (utils.sanitize("|.pdf").equals("_.pdf"));
		assert (utils.sanitize("[.pdf").equals("_.pdf"));
		assert (utils.sanitize("].pdf").equals("_.pdf"));
		assert (utils.sanitize("{.pdf").equals("_.pdf"));
		assert (utils.sanitize("}.pdf").equals("_.pdf"));
		assert (utils.sanitize("#.pdf").equals("_.pdf"));
		assert (utils.sanitize("~.pdf").equals("_.pdf"));
		assert (utils.sanitize("'.pdf").equals("_.pdf"));
		assert (utils.sanitize("(.pdf").equals("_.pdf"));
		assert (utils.sanitize("_.pdf").equals("_.pdf"));
		assert (utils.sanitize(").pdf").equals("_.pdf"));
		assert (utils.sanitize("=.pdf").equals("_.pdf"));
		assert (utils.sanitize("^.pdf").equals("_.pdf"));
		assert (utils.sanitize("$.pdf").equals("_.pdf"));
		assert (utils.sanitize("!.pdf").equals("_.pdf"));
		assert (utils.sanitize(":.pdf").equals("_.pdf"));
		assert (utils.sanitize(";.pdf").equals("_.pdf"));
		assert (utils.sanitize(",.pdf").equals("_.pdf"));
		assert (utils.sanitize("é.pdf").equals("_.pdf"));
	}
}
