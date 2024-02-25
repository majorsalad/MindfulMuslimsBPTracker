CREATE TABLE attendance (
    attendance_id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    date_attended DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student (student_id)
);