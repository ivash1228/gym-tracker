CREATE TABLE workout (
    id UUID PRIMARY KEY,
    client_id UUID,
    workout_date DATE,
    workout_name VARCHAR(30) NOT NULL,

    CONSTRAINT fk_client
        FOREIGN KEY (client_id)
        REFERENCES client(id)
        ON DELETE CASCADE
);