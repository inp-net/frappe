DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM majors) THEN -- Check if the table is empty
        INSERT INTO majors (uid, name, discontinued, school_id) VALUES 
            ('sdn', 'Sciences du Numérique', FALSE, 1),
			('eeea', 'Electronique, Energie Electrique & Automatique', FALSE, 1),
			('mfee', 'Mécanique des Fluides, Energétique & Environnement', FALSE, 1),
            ('bio', 'Ingénieur agronome', FALSE, 3);
    END IF;
END $$;
