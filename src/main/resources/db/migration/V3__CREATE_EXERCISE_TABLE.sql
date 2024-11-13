CREATE TABLE exercise (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type TINYINT NOT NULL,
    workout_id UUID,

    CONSTRAINT fk_workout
       FOREIGN KEY (workout_id)
       REFERENCES workout(id)
       ON DELETE CASCADE
);