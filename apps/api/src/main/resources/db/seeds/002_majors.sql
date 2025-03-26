DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM majors) THEN -- Check if the table is empty
        INSERT INTO majors (uid, name, discontinued, school_id) VALUES 
            ('sdn', 'Sciences du Numérique', FALSE, 1);
    END IF;
END $$;
