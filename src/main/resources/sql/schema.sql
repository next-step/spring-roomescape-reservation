CREATE TABLE reservation
(
    id     INTEGER      NOT NULL AUTO_INCREMENT,
    `date` DATE         NOT NULL,
    `time` TIME         NOT NULL,
    name   VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);
