DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM schools) THEN -- Check if the table is empty
        INSERT INTO schools (uid, name) VALUES 
            ('n7', 'ENSEEIHT'),
            ('a7', 'ENSIACET'),
            ('ensat', 'ENSAT');
    END IF;
END $$;
