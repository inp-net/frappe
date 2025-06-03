DO $$ 
BEGIN
	IF NOT EXISTS (SELECT 1 FROM tags) THEN -- Check if the table is empty
		INSERT INTO tags (name) VALUES
			('Cours'),
			('Examen'),
			('BE'),
			('TD'),
			('TP'),
			('Fiche');
	END IF;
END $$;
