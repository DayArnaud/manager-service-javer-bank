DROP USER if exists 'javer_user'@'%';

CREATE USER 'javer_user'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON javer_bank_db.* TO 'javer_user'@'%';

FLUSH PRIVILEGES;

