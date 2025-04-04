DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM tags) THEN -- Check if the table is empty
        INSERT INTO tags (name) VALUES 
            ('Test'),
            ('Toaster'),
            ('Wattouat');
    END IF;
END $$;
