-- liquibase formatted sql

-- changeset many_teaching_units_majors_minors:20250506150208-1
ALTER TABLE teaching_units DROP CONSTRAINT "FK7uaai0ghusw25hx0lvylovtok";

-- changeset many_teaching_units_majors_minors:20250506150208-2
ALTER TABLE teaching_units DROP CONSTRAINT "FKg97m9yhhddf3rajujm8w8n7qc";

-- changeset many_teaching_units_majors_minors:20250506150208-3
CREATE TABLE teaching_units_majors (teaching_unit_id BIGINT NOT NULL, major_id BIGINT NOT NULL);

-- changeset many_teaching_units_majors_minors:20250506150208-4
CREATE TABLE teaching_units_minors (teaching_unit_id BIGINT NOT NULL, minor_id BIGINT NOT NULL);

-- changeset many_teaching_units_majors_minors:20250506150208-5
ALTER TABLE teaching_units_majors ADD CONSTRAINT "FK1c3iirwvbvcl3k9eha4u1xyxd" FOREIGN KEY (major_id) REFERENCES majors (id);

-- changeset many_teaching_units_majors_minors:20250506150208-6
ALTER TABLE teaching_units_minors ADD CONSTRAINT "FK2n3ajrvjji8ygxfpass6gy9h2" FOREIGN KEY (teaching_unit_id) REFERENCES teaching_units (id);

-- changeset many_teaching_units_majors_minors:20250506150208-7
ALTER TABLE teaching_units_minors ADD CONSTRAINT "FK3su55jsc78iq6cu3b7fpnjtiq" FOREIGN KEY (minor_id) REFERENCES minors (id);

-- changeset many_teaching_units_majors_minors:20250506150208-8
ALTER TABLE teaching_units_majors ADD CONSTRAINT "FKdb49f6h9s2ja4pdwjdivoruhh" FOREIGN KEY (teaching_unit_id) REFERENCES teaching_units (id);

-- changeset many_teaching_units_majors_minors:20250506150208-9
ALTER TABLE teaching_units DROP COLUMN major_id;

-- changeset many_teaching_units_majors_minors:20250506150208-10
ALTER TABLE teaching_units DROP COLUMN minor_id;

