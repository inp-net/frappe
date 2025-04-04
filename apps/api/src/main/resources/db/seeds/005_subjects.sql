DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM subjects) THEN -- Check if the table is empty
        INSERT INTO subjects (name, teaching_unit_id) VALUES 
            ('Contrôle et Apprentissage', 1);
    END IF;
END $$;
