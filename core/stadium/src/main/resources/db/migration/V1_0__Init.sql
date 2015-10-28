CREATE TABLE IF NOT EXISTS users (
  id IDENTITY PRIMARY KEY ,
  username VARCHAR(50) UNIQUE ,
  mobile_phone CHAR(11) UNIQUE ,
  password VARCHAR(50) NOT NULL ,
  enabled BOOLEAN NOT NULL ,
  CONSTRAINT not_both_null CHECK (username IS NOT NULL OR mobile_phone IS NOT NULL) ,
  CONSTRAINT username_non_number CHECK (username NOT REGEXP '^\d{11}$') , -- Forbid mobilePhone number to be used as username
  CONSTRAINT phone_number CHECK (mobile_phone REGEXP '^\d{11}$')
);

CREATE TABLE IF NOT EXISTS authorities (
  id INTEGER NOT NULL ,
  authority VARCHAR(50) NOT NULL ,
  CONSTRAINT fk_authorities_users FOREIGN KEY (id) REFERENCES users(id)
);

-- CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username on authorities (username, authority) ;
