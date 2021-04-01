CREATE TABLE administration_authorities (
    user_account_id VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user_accounts (
    user_account_id VARCHAR(255) NOT NULL,
    account_name    VARCHAR(255) NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,
    creator_id      VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_account_id),
    UNIQUE (account_name),
    FOREIGN KEY (creator_id) REFERENCES administration_authorities(user_account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE chat_rooms (
  room_id         VARCHAR(255) NOT NULL,
  room_name       VARCHAR(255) NOT NULL,
  room_owner_id   VARCHAR(255) NOT NULL,
  created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (room_id),
  FOREIGN KEY (room_owner_id) REFERENCES administration_authorities(user_account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE room_members (
    user_account_id  VARCHAR(255) NOT NULL,
    member_name      VARCHAR(255) NOT NULL,
    room_id          VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_account_id, room_id),
    FOREIGN KEY (user_account_id) REFERENCES user_accounts(user_account_id),
    FOREIGN KEY (room_id)         REFERENCES chat_rooms(room_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE messages (
    message_id      VARCHAR(255) NOT NULL,
    content         TEXT         NOT NULL,
    sent_date_time  TIMESTAMP    NOT NULL,
    sender_id       VARCHAR(255) NOT NULL,
    room_id         VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (message_id),
    FOREIGN KEY (sender_id) REFERENCES room_members(user_account_id),
    FOREIGN KEY (room_id)   REFERENCES chat_rooms(room_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
