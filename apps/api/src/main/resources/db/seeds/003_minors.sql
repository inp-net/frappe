DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM minors) THEN -- Check if the table is empty
        INSERT INTO minors (name, major_id) VALUES
			('Tronc Commun - 1A', 1),
			('Architecture, Systèmes et Réseaux', 1),
			('HPC et Big Data', 1),
			('Image et Multimédia', 1),
			('Réseaux', 1),
			('Systèmes Logiciels', 1),
			('Systèmes de Télécommunications', 1),
			('Agrobiosciences Végétales', 4);
    END IF;
END $$;
