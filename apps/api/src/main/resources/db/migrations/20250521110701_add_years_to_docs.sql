-- liquibase formatted sql

-- changeset add_years_to_docs:20250521110701-1
ALTER TABLE documents ADD year INTEGER NOT NULL;

