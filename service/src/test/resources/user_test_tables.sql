DROP TABLE IF EXISTS structureinventorysystem.user_roles;
DROP TABLE IF EXISTS structureinventorysystem.users;

CREATE TABLE structureinventorysystem.users (
  id bigint NOT NULL AUTO_INCREMENT,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  academic_degree varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE structureinventorysystem.user_roles (
  user_id bigint NOT NULL,
  roles varchar(255) DEFAULT NULL,
  CONSTRAINT `FK_users_user_roles` FOREIGN KEY (user_id) REFERENCES structureinventorysystem.users (id)
);


