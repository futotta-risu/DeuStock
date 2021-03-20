DROP SCHEMA deustockdb;
DROP USER 'deustock_user1'@'%';

CREATE SCHEMA deustockdb;
CREATE USER 'deustock_user1'@'%' IDENTIFIED BY '12345';
GRANT ALL ON deustockdb.* TO 'deustock_user1'@'%';