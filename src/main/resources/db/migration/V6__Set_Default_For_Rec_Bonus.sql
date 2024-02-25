ALTER TABLE student
ALTER COLUMN recruitment_bonus SET DEFAULT 0;

UPDATE student
SET recruitment_bonus = 0
WHERE recruitment_bonus IS NULL;
