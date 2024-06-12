DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS reservation_time;


CREATE TABLE reservation
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    date_time DATETIME     NOT NULL,
    time_id   BIGINT,

    PRIMARY KEY (id)
);

CREATE TABLE reservation_time
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    start_at TIME   NOT NULL,
    PRIMARY KEY (id)
);
