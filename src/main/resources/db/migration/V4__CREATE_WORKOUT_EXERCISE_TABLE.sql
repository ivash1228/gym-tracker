CREATE TABLE workout_exercise (
    id UUID PRIMARY KEY,
    workout_id UUID,
    exercise_id UUID,
    exercise_order INTEGER,
    UNIQUE (workout_id, exercise_id)
);