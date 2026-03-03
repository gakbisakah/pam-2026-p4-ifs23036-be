CREATE TABLE IF NOT EXISTS movies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    director VARCHAR(255) NOT NULL,
    release_year INTEGER NOT NULL,
    genre VARCHAR(100) NOT NULL,
    rating DOUBLE PRECISION DEFAULT 0.0,
    description TEXT,
    poster_path VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

