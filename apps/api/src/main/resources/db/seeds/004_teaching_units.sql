DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM teaching_units) THEN -- Check if the table is empty
        INSERT INTO teaching_units (name) VALUES 
            ('Science des Réseaux et Apprentissage');
    END IF;
END $$;
