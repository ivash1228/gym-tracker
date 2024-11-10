CREATE TABLE workout_exercise (
    id UUID PRIMARY KEY,
    workout_id UUID,
    exercise_id UUID,
    order INT,
    sets INT,
    weights INT,
    reps_count INT
);