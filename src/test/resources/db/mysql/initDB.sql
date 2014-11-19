/*DROP DATABASE IF EXISTS ip_test;*/

/*CREATE DATABASE IF NOT EXISTS ip_test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;*/

USE ip_test;

-- ----------------------------
-- Table structure for `country_ip`
-- ----------------------------
drop table if exists country_ip;
create table country_ip(
	id int(10) not null auto_increment,
	start_ip double,
	end_ip double,
	code2 varchar(255),
	start_ip1 varchar(255),
	end_ip1 varchar(255),
	country_cn varchar(255),
	primary key(id)
) engine=InnoDB;


