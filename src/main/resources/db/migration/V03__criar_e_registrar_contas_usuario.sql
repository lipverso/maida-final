CREATE TABLE user_account (
	id_user BIGINT(20) NOT NULL,
	id_account BIGINT(20) NOT NULL,
	PRIMARY KEY (id_user, id_account),
	FOREIGN KEY (id_user) REFERENCES user(id),
	FOREIGN KEY (id_account) REFERENCES account(id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;