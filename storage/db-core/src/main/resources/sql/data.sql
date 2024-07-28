INSERT INTO reservation_times (time_id, start_at, created_at)
VALUES (300, '10:21:00', '2024-06-25T10:15:30');

/**
  INSERT reservations
 */
INSERT INTO reservations(reservation_id, name, date, time_id, status, canceled_at, created_at)
VALUES (101, 'name1', '2024-06-25', 300, 'CONFIRMED', null, '2024-06-25T10:15:30');

INSERT INTO reservations(reservation_id, name, date, time_id, status, canceled_at, created_at)
VALUES (102, 'name2', '2024-06-25', 300, 'CANCELED', '2024-06-25T10:15:31', '2024-06-25T10:15:30');
