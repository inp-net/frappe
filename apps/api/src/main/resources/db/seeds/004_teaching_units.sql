DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM teaching_units) THEN -- Check if the table is empty
        INSERT INTO teaching_units (name, minor_id) VALUES 
            ('Science des Réseaux et Apprentissage', 1);
    END IF;
END $$;
