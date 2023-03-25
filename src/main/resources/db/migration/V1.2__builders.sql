CREATE TABLE IF NOT EXISTS mev_builders
(
    id      SERIAL PRIMARY KEY,
    address varchar(42) UNIQUE,
    name    varchar(100)
);
