ALTER TABLE student
ALTER COLUMN attendance_bonus SET DEFAULT 0;

UPDATE student
SET attendance_bonus = 0
WHERE attendance_bonus IS NULL;
