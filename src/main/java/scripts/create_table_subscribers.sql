CREATE TABLE IF NOT EXISTS subscribers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    reference VARCHAR(255) NOT NULL
);

INSERT INTO subscribers (name, reference) VALUES
    ('Subscriber_1', 'REF1'),
    ('Subscriber_2', 'REF2'),
    ('Subscriber_3', 'REF3');