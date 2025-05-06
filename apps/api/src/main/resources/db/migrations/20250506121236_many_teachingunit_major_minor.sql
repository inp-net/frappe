-- liquibase formatted sql

-- changeset many_teachingunit_major_minor:20250506121236-1
ALTER TABLE teaching_units DROP CONSTRAINT "FK7uaai0ghusw25hx0lvylovtok";

-- changeset many_teachingunit_major_minor:20250506121236-2
ALTER TABLE teaching_units DROP CONSTRAINT "FKg97m9yhhddf3rajujm8w8n7qc";

-- changeset many_teachingunit_major_minor:20250506121236-3
CREATE TABLE teaching_units_majors ("teachingUnits_id" BIGINT NOT NULL, majors_id BIGINT NOT NULL);

-- changeset many_teachingunit_major_minor:20250506121236-4
CREATE TABLE teaching_units_minors ("teachingUnits_id" BIGINT NOT NULL, minors_id BIGINT NOT NULL);

-- changeset many_teachingunit_major_minor:20250506121236-5
ALTER TABLE teaching_units_minors ADD CONSTRAINT "FKboi013g7afq97cv3bf4qw3r4q" FOREIGN KEY ("teachingUnits_id") REFERENCES teaching_units (id);

-- changeset many_teachingunit_major_minor:20250506121236-6
ALTER TABLE teaching_units_minors ADD CONSTRAINT "FKfcicas7xhtqmodokxtc85s8ar" FOREIGN KEY (minors_id) REFERENCES minors (id);

-- changeset many_teachingunit_major_minor:20250506121236-7
ALTER TABLE teaching_units_majors ADD CONSTRAINT "FKg10vuuxb6eqtrv2r5qfo5v7ln" FOREIGN KEY (majors_id) REFERENCES majors (id);

-- changeset many_teachingunit_major_minor:20250506121236-8
ALTER TABLE teaching_units_majors ADD CONSTRAINT "FKmdliebq20s2qgy52l6ygmooq7" FOREIGN KEY ("teachingUnits_id") REFERENCES teaching_units (id);

-- changeset many_teachingunit_major_minor:20250506121236-9
ALTER TABLE teaching_units DROP COLUMN major_id;

-- changeset many_teachingunit_major_minor:20250506121236-10
ALTER TABLE teaching_units DROP COLUMN minor_id;

