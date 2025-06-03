DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM subjects) THEN -- Check if the table is empty
        INSERT INTO subjects (name) VALUES 
            ('Contrôle et Apprentissage'),
            ('De la Plante aux Produits de santé'),
            ('Conférence ou Colloque Végéphyl'),
            ('Intégration et applications'),
            ('Probabilités'),
            ('Programmation impérative'),
            ('Programmation impérative 2'),
            ('Statistiques'),
            ('EDP'),
            ('Optimisation');
    END IF;
	IF NOT EXISTS (SELECT 1 FROM subjects_teaching_units) THEN -- Check if the table is empty
        INSERT INTO subjects_teaching_units (teaching_unit_id, subject_id) VALUES 
            (1, 1),
            (3, 2),
            (3, 3),
            (4, 4),
            (4, 5),
            (5, 6),
            (5, 7),
            (6, 8),
            (6, 9),
            (6, 10);
    END IF;
END $$;
