CREATE TABLE reservation
(
    id     INTEGER      NOT NULL AUTO_INCREMENT,
    `date` DATE         NOT NULL,
    `time` TIME         NOT NULL,
    name   VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE theme
(
    id          INTEGER       NOT NULL AUTO_INCREMENT,
    price       LONG          NOT NULL,
    name        VARCHAR(1000) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE schedule
(
    id       INTEGER NOT NULL AUTO_INCREMENT,
    theme_id INTEGER NOT NULL,
    `date`   DATE    NOT NULL,
    `time`   TIME    NOT NULL,
    PRIMARY KEY (id)
);