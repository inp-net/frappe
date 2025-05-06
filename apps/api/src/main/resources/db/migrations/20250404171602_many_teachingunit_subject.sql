-- liquibase formatted sql

-- changeset many_teachingunit_subject:20250404171602-1
ALTER TABLE subjects DROP CONSTRAINT "FK1jlvab3bf3hhxl2yteiy4mdfj";

-- changeset many_teachingunit_subject:20250404171602-2
CREATE TABLE subjects_teaching_units (teaching_unit_id BIGINT NOT NULL, subject_id BIGINT NOT NULL);

-- changeset many_teachingunit_subject:20250404171602-3
ALTER TABLE subjects_teaching_units ADD CONSTRAINT "FK560p4sn5f9qxmgs7m2oxae06q" FOREIGN KEY (teaching_unit_id) REFERENCES teaching_units (id);

-- changeset many_teachingunit_subject:20250404171602-4
ALTER TABLE subjects_teaching_units ADD CONSTRAINT "FKfv3qxpaff6hxv5weogmysmr3g" FOREIGN KEY (subject_id) REFERENCES subjects (id);

-- changeset many_teachingunit_subject:20250404171602-5
ALTER TABLE subjects DROP COLUMN teaching_unit_id;

