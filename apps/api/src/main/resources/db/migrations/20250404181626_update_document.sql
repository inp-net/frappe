-- liquibase formatted sql

-- changeset update_document:20250404181626-1
CREATE TABLE documents_user (user_id UUID, document_id UUID NOT NULL, CONSTRAINT "documents_userPK" PRIMARY KEY (document_id));

-- changeset update_document:20250404181626-2
CREATE TABLE files (id UUID NOT NULL, extension VARCHAR(255), name VARCHAR(255), document_id UUID, CONSTRAINT "filesPK" PRIMARY KEY (id));

-- changeset update_document:20250404181626-3
ALTER TABLE documents_user ADD CONSTRAINT "FKg9d0awg8g61blitmlf8xsb5xt" FOREIGN KEY (document_id) REFERENCES documents (id);

-- changeset update_document:20250404181626-4
ALTER TABLE documents_user ADD CONSTRAINT "FKkk10l10hnws2kctqau0e00ktq" FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset update_document:20250404181626-5
ALTER TABLE files ADD CONSTRAINT "FKyxr6yly4wf6ansn30n5qu1tj" FOREIGN KEY (document_id) REFERENCES documents (id);

