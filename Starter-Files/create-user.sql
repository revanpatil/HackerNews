CREATE USER 'hackeruser'@'localhost' IDENTIFIED BY 'hackeruser';

GRANT ALL PRIVILEGES ON * . * TO 'hackeruser'@'localhost';

# See the MySQL Reference Manual for details: 
# https://dev.mysql.com/doc/refman/8.0/en/caching-sha2-pluggable-authentication.html
#
ALTER USER 'hackeruser'@'localhost' IDENTIFIED WITH mysql_native_password BY 'hackeruser';