CREATE TABLE IF NOT EXISTS author (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    sex CHAR(1) CHECK (sex IN ('M', 'F'))
);

INSERT INTO author (name, sex) VALUES ('John Smith', 'M');
INSERT INTO author (name, sex) VALUES ('Emily Johnson', 'F');
INSERT INTO author (name, sex) VALUES ('Alex Taylor', 'M');
