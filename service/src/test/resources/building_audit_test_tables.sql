DROP TABLE IF EXISTS structureinventorysystem.building_audits;
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

CREATE TABLE structureinventorysystem.building_audits (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  structure_type varchar(255) NOT NULL,
  location varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  created_on date NOT NULL,
  created_by_user_id bigint NOT NULL,
  updated_on date DEFAULT NULL,
  updated_by_user_id bigint DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT `FK_audit_created_by_user_id` FOREIGN KEY (created_by_user_id) REFERENCES structureinventorysystem.users (id),
  CONSTRAINT `FK_audit_updated_by_user_id` FOREIGN KEY (updated_by_user_id) REFERENCES structureinventorysystem.users (id)
);
