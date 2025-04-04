DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM minors) THEN -- Check if the table is empty
        INSERT INTO minors (name, major_id) VALUES 
            ('Architecture système et rézo', 1);
    END IF;
END $$;
