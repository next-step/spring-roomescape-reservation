DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS scheduleId;
DROP TABLE IF EXISTS themes;

CREATE TABLE themes
(
    id    INT         NOT NULL AUTO_INCREMENT,
    name  VARCHAR(20) NOT NULL,
    desc  VARCHAR(20) NOT NULL,
    price INT         NOT NULL,
    DELETED BOOLEAN   NOT NULL   DEFAULT  0,
    PRIMARY KEY (id)
);

CREATE TABLE schedules
(
    id      INT  NOT NULL AUTO_INCREMENT,
    themeId INT  NOT NULL,
    date    DATE NOT NULL,
    time    TIME NOT NULL,
    CONSTRAINT UC_SCHEDULES UNIQUE (themeId, date, time),
    FOREIGN KEY (themeId) REFERENCES themes(id),
    PRIMARY KEY (id)
);

CREATE TABLE reservations
(
    id         INT         NOT NULL AUTO_INCREMENT,
    scheduleId INT         NOT NULL,
    name       VARCHAR(20) NOT NULL,
    CONSTRAINT UC_RESERVATIONS UNIQUE (scheduleId),
    FOREIGN KEY (scheduleId) REFERENCES schedules(id),
    PRIMARY KEY (id)
);
