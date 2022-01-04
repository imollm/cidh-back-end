
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4a', 'Category 1', 'Description 1', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4b', 'Category 2', 'Description 2', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4c', 'Category 3', 'Description 3', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4d', 'Category 4', 'Description 4', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4e', 'Category 5', 'Description 5', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4f', 'Category 6', 'Description 6', '2021-03-31 09:30:20-07');

INSERT INTO label VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4a', 'Label 1', 'Description 1', '2021-03-31 09:30:20-07');
INSERT INTO label VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4b', 'Label 2', 'Description 2', '2021-03-31 09:30:20-07');
INSERT INTO label VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4c', 'Label 3', 'Description 3', '2021-03-31 09:30:20-07');
INSERT INTO label VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4d', 'Label 4', 'Description 4', '2021-03-31 09:30:20-07');
INSERT INTO label VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4e', 'Label 5', 'Description 5', '2021-03-31 09:30:20-07');
INSERT INTO label VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4f', 'Label 6', 'Description 6', '2021-03-31 09:30:20-07');

-- Create a user with role SUPERADMIN
INSERT INTO "user" VALUES ('d578fae9-d222-4o37-a5b0-46f9128beb4f', 'SuperAdmin', 'SuperAdmin', null, null, 'superadmin@indahou.se', null, true, 'English', '$2a$12$cYd7Izho3U1SdKloTKY83e0QUYZS9dAcVlW45CdjkYr1EHFPH.ymK', NOW(), NOW());
INSERT INTO user_role VALUES ('d5864ae9-d386-4o37-a5b0-46f9120beb4f', 'd578fae9-d222-4o37-a5b0-46f9128beb4f', 'SUPERADMIN');
-- Create a user with role ADMIN
INSERT INTO "user" VALUES ('d578fae9-d376-4o37-a5b0-46f9128beb4f', 'Admin', 'Admin', null, null, 'admin@indahou.se', null, true, 'English', '$2a$12$HbyTXCCqe6g40P58rnmwAerxkxHS2yyIAhFF2FNMgCD1WYY9trGH2', NOW(), NOW());
INSERT INTO user_role VALUES ('d578fae9-d386-4o37-a5b0-46f9120beb4f', 'd578fae9-d376-4o37-a5b0-46f9128beb4f', 'ADMIN');
-- Create a user with role USER
INSERT INTO "user" VALUES ('d578fae9-d376-4o37-a5b0-46f9128beb4g', 'User', 'User', null, null, 'user@indahou.se', null, true, 'English', '$2a$12$MEca2otGtxx.BRZZ04D5vuOEyCG5xUJ/TQuIvleGqOrjGop/YvgzW', NOW(), NOW());
INSERT INTO user_role VALUES ('d578fae9-d386-4o37-a5b0-46f9120beb45', 'd578fae9-d376-4o37-a5b0-46f9128beb4g', 'USER');

INSERT INTO event_organizer VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb4f', 'Event organizer 1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'd578fae9-d376-4o37-a5b0-46f9128beb4f', NOW());
INSERT INTO event_organizer VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb41', 'Event organizer 2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'd578fae9-d376-4o37-a5b0-46f9128beb4f', NOW());
INSERT INTO event_organizer VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb42', 'Event organizer 3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'd578fae9-d376-4o37-a5b0-46f9128beb4f', NOW());
INSERT INTO event_organizer VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb43', 'Event organizer 4', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'd578fae9-d376-4o37-a5b0-46f9128beb4f', NOW());
INSERT INTO event_organizer VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb44', 'Event organizer 5', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'd578fae9-d376-4o37-a5b0-46f9128beb4f', NOW());
INSERT INTO event_organizer VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb45', 'Event organizer 6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'd578fae9-d376-4o37-a5b0-46f9128beb4f', NOW());

DELETE FROM event WHERE id in ('d578fae9-d376-4e37-a5b0-46f9128beb40',
    'd578fae9-d376-4e37-a5b0-46f9128beb41',
    'd578fae9-d376-4e37-a5b0-46f9128beb42',
    'd578fae9-d376-4e37-a5b0-46f9128beb43',
    'd578fae9-d376-4e37-a5b0-46f9128beb44',
    'd578fae9-d376-4e37-a5b0-46f9128beb45');

INSERT INTO event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb40', 'Event 1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'https://www.masquenegocio.com/wp-content/uploads/2018/03/evento-concierto-874x492.jpg', (NOW() - interval '2 days'), (NOW() - interval '1 days'), 'd578fae9-d376-4e37-a5b0-46f9128beb4f', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4a', 'https://www.youtube.com/embed/3thisaqhzW8');
INSERT INTO event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb41', 'Event 2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'https://www.masquenegocio.com/wp-content/uploads/2018/03/evento-concierto-874x492.jpg', (NOW() - interval '2 days'), (NOW() + interval '3 days'), 'd578fae9-d376-4e37-a5b0-46f9128beb41', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4b', 'https://www.youtube.com/embed/gH8PrzEM5_M');
INSERT INTO event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb42', 'Event 3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'https://www.masquenegocio.com/wp-content/uploads/2018/03/evento-concierto-874x492.jpg', (NOW() - interval '2 days'), (NOW() + interval '3 days'), 'd578fae9-d376-4e37-a5b0-46f9128beb42', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4c', 'https://www.youtube.com/embed/x_uT79nxcVg');
INSERT INTO event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb43', 'Event 4', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'https://www.masquenegocio.com/wp-content/uploads/2018/03/evento-concierto-874x492.jpg', (NOW() + interval '1 days'), (NOW() + interval '3 days'), 'd578fae9-d376-4e37-a5b0-46f9128beb43', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4d', 'https://www.youtube.com/embed/riy86dFDfnQ');
INSERT INTO event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb44', 'Event 5', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'https://www.masquenegocio.com/wp-content/uploads/2018/03/evento-concierto-874x492.jpg', (NOW() + interval '1 days'), (NOW() + interval '3 days'), 'd578fae9-d376-4e37-a5b0-46f9128beb44', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4e', 'https://www.youtube.com/embed/mXfSg7mLEdU');
INSERT INTO event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb45', 'Event 6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pellentesque sed sapien nec efficitur. Integer aliquet vehicula justo non tempus.', 'https://www.masquenegocio.com/wp-content/uploads/2018/03/evento-concierto-874x492.jpg', (NOW() + interval '1 days'), (NOW() + interval '3 days'), 'd578fae9-d376-4e37-a5b0-46f9128beb45', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4f', 'https://www.youtube.com/embed/7eMfgS7rx_4');

INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb40', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4a');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb40', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4b');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb41', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4c');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb41', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4d');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb42', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4e');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb42', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4f');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb43', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4a');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb43', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4b');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb44', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4c');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb44', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4d');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb45', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4e');
INSERT INTO label_event VALUES ('d578fae9-d376-4e37-a5b0-46f9128beb45', 'd5a8fae9-d376-4e37-a5b0-46f9128beb4f');
