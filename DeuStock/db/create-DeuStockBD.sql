DROP SCHEMA IF EXISTS deustockdb;
DROP USER IF EXISTS 'deustock_user1'@'%';

CREATE SCHEMA deustockdb;
CREATE USER 'deustock_user1'@'%' IDENTIFIED BY '12345';
GRANT ALL ON deustockdb.* TO 'deustock_user1'@'%';