ALTER TABLE student
DROP COLUMN total_bonus;

ALTER TABLE student
ADD COLUMN total_bonus DOUBLE PRECISION GENERATED ALWAYS AS (attendance_bonus + recruitment_bonus) STORED;
