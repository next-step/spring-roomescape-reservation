CREATE TABLE IF NOT EXISTS reservation
(
    id   INT         NOT NULL AUTO_INCREMENT,
    date DATE        NOT NULL,
    time TIME        NOT NULL,
    name VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS theme
(
    id          INT         NOT NULL AUTO_INCREMENT,
    name        VARCHAR(10) NOT NULL,
    description VARCHAR(20) NOT NULL,
    price       INT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS schedule
(
    id       INT  NOT NULL AUTO_INCREMENT,
    theme_id INT  NOT NULL,
    date     DATE NOT NULL,
    time     TIME NOT NULL,
    PRIMARY KEY (id)
);