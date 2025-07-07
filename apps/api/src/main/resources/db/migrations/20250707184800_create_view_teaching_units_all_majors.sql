-- Improve performance when using majorId filter on /document:
-- add view that maps teaching_unit to major_id using both
-- direct (major_id column) and indirect (minor_id column -> major_id from minor)
CREATE VIEW teaching_units_all_majors AS (
	SELECT teaching_unit_id, major_id FROM teaching_units_majors
) UNION (
	SELECT teaching_unit_id, major_id FROM minors m JOIN teaching_units_minors tum ON m.id = tum.minor_id
);
