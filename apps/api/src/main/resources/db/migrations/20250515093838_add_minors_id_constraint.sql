-- liquibase formatted sql

-- changeset add_minors_id_constraint:20250515093838-1
ALTER TABLE minors ADD CONSTRAINT "FKc855bwo2dsx36ts44qrogajds" FOREIGN KEY (id) REFERENCES minors (id);

