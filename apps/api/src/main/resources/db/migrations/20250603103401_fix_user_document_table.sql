-- liquibase formatted sql

-- changeset fix_user_document_table:20250603103401-1
ALTER TABLE documents_user DROP CONSTRAINT "FKg9d0awg8g61blitmlf8xsb5xt";

-- changeset fix_user_document_table:20250603103401-2
ALTER TABLE documents_user DROP CONSTRAINT "FKkk10l10hnws2kctqau0e00ktq";

-- changeset fix_user_document_table:20250603103401-3
ALTER TABLE documents ADD author_id UUID;

-- changeset fix_user_document_table:20250603103401-4
ALTER TABLE documents ADD CONSTRAINT "FKouq4c7m9dkafopniieriedyxh" FOREIGN KEY (author_id) REFERENCES users (id);

-- changeset fix_user_document_table:20250603103401-5
DROP TABLE documents_user;

