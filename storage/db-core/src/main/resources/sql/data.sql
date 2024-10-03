/**
  INSERT reservation_times
 */
INSERT INTO reservation_times (time_id, start_at, created_at)
VALUES (300, '10:21:00', '2024-06-25T10:15:30');

/**
  INSERT themes
 */
INSERT INTO themes(theme_id, name, description, thumbnail, active_status)
values (1, 'theme1', 'theme1_descprition', 'https://thumbnail.theme1.com', 'ACTIVE');

INSERT INTO themes(theme_id, name, description, thumbnail, active_status)
values (2, 'theme2', 'theme2_descprition', 'https://thumbnail.theme2.com', 'ACTIVE');

INSERT INTO themes(theme_id, name, description, thumbnail, active_status)
values (3, 'theme3', 'theme3_descprition', 'https://thumbnail.theme3.com', 'ACTIVE');

/**
  INSERT reservations
 */
INSERT INTO reservations(reservation_id, name, date, time_id, theme_id, active_status, deleted_at, created_at)
VALUES (101, 'name1', '2024-06-25', 300, 1, 'ACTIVE', null, '2024-06-25T10:15:30');

INSERT INTO reservations(reservation_id, name, date, time_id, theme_id, active_status, deleted_at, created_at)
VALUES (102, 'name2', '2024-06-25', 300, 2, 'DELETED', '2024-06-25T10:15:31', '2024-06-25T10:15:30');
