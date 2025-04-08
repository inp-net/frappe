DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM subjects) THEN -- Check if the table is empty
        INSERT INTO subjects (name) VALUES 
            ('Contrôle et Apprentissage');
    END IF;
	IF NOT EXISTS (SELECT 1 FROM subjects_teaching_units) THEN -- Check if the table is empty
        INSERT INTO subjects_teaching_units (teaching_unit_id, subject_id) VALUES 
            (1, 1);
    END IF;
END $$;
