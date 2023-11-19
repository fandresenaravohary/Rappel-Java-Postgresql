CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    book_name VARCHAR(255) NOT NULL,
    page_numbers INT NOT NULL,
    sex CHAR(1) CHECK (sex IN ('M', 'F')),
    topic VARCHAR(10) CHECK (topic IN ('comedy', 'romance', 'other')),
    release_date DATE,
    author_id BIGINT REFERENCES author(id)
);

INSERT INTO book (book_name, page_numbers, sex, topic, release_date, author_id)
VALUES ('Laughter Unlimited', 300, 'M', 'comedy', '2023-01-01', 1);

INSERT INTO book (book_name, page_numbers, sex, topic, release_date, author_id)
VALUES ('Eternal Love', 400, 'F', 'romance', '2023-02-15', 2);

INSERT INTO book (book_name, page_numbers, sex, topic, release_date, author_id)
VALUES ('Beyond the Stars', 500, 'M', 'other', '2023-03-30', 3);
