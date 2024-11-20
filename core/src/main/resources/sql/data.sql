/**
  INSERT reservation_times
 */
INSERT INTO reservation_times (time_id, start_at, created_at)
VALUES (300, '10:00:00', '2024-06-25T10:15:30');

INSERT INTO reservation_times (time_id, start_at, created_at)
VALUES (400, '11:00:00', '2024-06-25T10:15:30');


/**
  INSERT themes
 */
INSERT INTO themes(theme_id, name, description, thumbnail, deleted)
values (100, 'theme1', 'theme1_descprition', 'https://thumbnail.theme1.com', false);

INSERT INTO themes(theme_id, name, description, thumbnail, deleted)
values (101, 'theme2', 'theme2_descprition', 'https://thumbnail.theme2.com', false);

INSERT INTO themes(theme_id, name, description, thumbnail, deleted)
values (102, 'theme3', 'theme3_descprition', 'https://thumbnail.theme3.com', false);

/**
  INSERT reservations
 */
INSERT INTO reservations(reservation_id, name, date, time_id, theme_id, status, reserved_at, canceled_at)
VALUES (101, 'name1', '2024-06-25', 300, 100, 'CONFIRMED', '2024-06-25T10:15:31', '2024-06-25T10:15:30');

INSERT INTO reservations(reservation_id, name, date, time_id, theme_id, status, reserved_at, canceled_at)
VALUES (102, 'name2', '2024-06-25', 300, 101, 'CANCELED', '2024-06-25T10:15:31', '2024-06-30T10:15:30');
