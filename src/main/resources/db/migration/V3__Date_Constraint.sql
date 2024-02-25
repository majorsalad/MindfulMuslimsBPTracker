ALTER TABLE attendance
ADD CONSTRAINT unique_student_date UNIQUE(student_id, date_attended);