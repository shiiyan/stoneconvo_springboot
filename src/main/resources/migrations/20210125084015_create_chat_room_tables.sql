CREATE TABLE chat_rooms (
  room_id       VARCHAR(255) NOT NULL,
  room_name     VARCHAR(255) NOT NULL,
  room_owner_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (room_id),
  FOREIGN KEY (room_owner_id) REFERENCES administrators(user_account_id)
);

--CREATE TABLE room_members (
--
--);
--
--CREATE TABLE messages (
--  message_id VARCHAR(255) NOT NULL,
--  content TINYTEXT NOT NULL,
--  room_id VARCHAR(255) NOT NULL,
--    PRIMARY KEY (message_id)
--  FOREIGN KEY (room_id) REFERENCES chat_rooms(room_id)
--);