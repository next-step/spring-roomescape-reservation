CREATE TABLE IF NOT EXISTS reservation
(
    id   INT         NOT NULL AUTO_INCREMENT,
    date DATE        NOT NULL,
    time TIME        NOT NULL,
    name VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);