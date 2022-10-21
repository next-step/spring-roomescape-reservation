CREATE TABLE theme
(
    id     INTEGER      NOT NULL AUTO_INCREMENT,
    name   VARCHAR(256) NOT NULL,
    `desc` VARCHAR(256) NOT NULL,
    price  INTEGER      NOT NULL,
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


CREATE TABLE reservation
(
    id          INTEGER      NOT NULL AUTO_INCREMENT,
    schedule_id INTEGER      NOT NULL,
    `date`      DATE         NOT NULL,
    `time`      TIME         NOT NULL,
    name        VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);
