DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM teaching_units) THEN -- Check if the table is empty
        INSERT INTO teaching_units (name) VALUES 
            ('Science des Réseaux et Apprentissage');
		INSERT INTO teaching_units (name) VALUES 
            ('Interconnexion et modélisation des réseaux');
	END IF;
	IF NOT EXISTS (SELECT 1 FROM teaching_units_majors) THEN -- Check if the table is empty
        INSERT INTO teaching_units_majors (teaching_unit_id, major_id) VALUES 
            (1, 1);
    END IF;
	IF NOT EXISTS (SELECT 1 FROM teaching_units_minors) THEN -- Check if the table is empty
        INSERT INTO teaching_units_minors (teaching_unit_id, minor_id) VALUES 
            (2, 1);
    END IF;
END $$;
