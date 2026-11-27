CREATE TABLE IF NOT EXISTS course (
    id             SERIAL PRIMARY KEY,
    title          VARCHAR(255) NOT NULL UNIQUE,
    duration_hours INTEGER      NOT NULL CHECK (duration_hours > 0)
);
