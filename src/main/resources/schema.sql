DROP TABLE IF EXISTS reservations;

CREATE TABLE themes
(
    id          INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(20)  NOT NULL,
    description VARCHAR(255) NOT NULL,
    price       INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE schedules
(
    id       INT  NOT NULL AUTO_INCREMENT,
    theme_id INT  NOT NULL,
    date     DATE NOT NULL,
    time     TIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservations
(
    id          INT         NOT NULL AUTO_INCREMENT,
    name        VARCHAR(20) NOT NULL,
    schedule_id INT         NOT NULL,
    PRIMARY KEY (id)
);
