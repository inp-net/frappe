DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM teaching_units) THEN -- Check if the table is empty
        INSERT INTO teaching_units (name) VALUES 
            ('Science des Réseaux et Apprentissage');
		INSERT INTO teaching_units (name) VALUES 
            ('Interconnexion et Modélisation des Réseaux');
        INSERT INTO teaching_units (name) VALUES
            ('Innovation et Valorisation de la Recherche en Production Végétales');
        INSERT INTO teaching_units (name) VALUES
            ('Intégration et Applications - Probabilités');
        INSERT INTO teaching_units (name) VALUES
            ('Programmation Impérative');
        INSERT INTO teaching_units (name) VALUES
            ('Analyse Numérique - Statistiques');
        INSERT INTO teaching_units (name) VALUES
            ('Automatique - Analyse de Données');
        INSERT INTO teaching_units (name) VALUES
            ('Modélisation - Architecture');
        INSERT INTO teaching_units (name) VALUES
            ('Soutien');
        INSERT INTO teaching_units (name) VALUES
            ('Traitement du Signal - Télécommunications');
        INSERT INTO teaching_units (name) VALUES
            ('Réseaux');
        INSERT INTO teaching_units (name) VALUES
            ('Calcul scientifique - Apprentissage');
        INSERT INTO teaching_units (name) VALUES
            ('Technologie Objet');
        INSERT INTO teaching_units (name) VALUES
            ('Architecture Système');
	END IF;
	-- IF NOT EXISTS (SELECT 1 FROM teaching_units_majors) THEN -- Check if the table is empty
    --     INSERT INTO teaching_units_majors (teaching_unit_id, major_id) VALUES 
            -- If seeding from linking teaching units and majors => Insert here >>
    -- END IF;
	IF NOT EXISTS (SELECT 1 FROM teaching_units_minors) THEN -- Check if the table is empty
        INSERT INTO teaching_units_minors (teaching_unit_id, minor_id) VALUES 
            (1, 2),
            (2, 2),
            (3, 8),
            (4, 1),
            (5, 1),
            (6, 1),
            (7, 1),
            (8, 1),
            (9, 1),
            (10, 1),
            (11, 1),
            (12, 1),
            (13, 1),
            (14, 1);
    END IF;
END $$;
