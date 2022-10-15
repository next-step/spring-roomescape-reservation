DROP TABLE IF EXISTS reservations;

CREATE TABLE reservations
(
    id   INT         NOT NULL  AUTO_INCREMENT,
    date DATE        NOT NULL,
    time TIME        NOT NULL,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);
