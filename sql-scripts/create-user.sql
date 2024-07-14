DROP USER if exists 'javer_user'@'localhost' ;

CREATE USER 'javer_user'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON javer_bank_db.* TO 'javer_user'@'localhost';

FLUSH PRIVILEGES;

SELECT user, host FROM mysql.user;

