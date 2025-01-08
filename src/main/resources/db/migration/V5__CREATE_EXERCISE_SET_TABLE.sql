CREATE TABLE exercise_set (
    id UUID PRIMARY KEY,
    workout_exercise_id UUID,
    weights INTEGER,
    reps INTEGER,
    set_order INTEGER,

        CONSTRAINT fk_workout_exercise
            FOREIGN KEY (workout_exercise_id)
            REFERENCES workout_exercise(id)
            ON DELETE CASCADE
);