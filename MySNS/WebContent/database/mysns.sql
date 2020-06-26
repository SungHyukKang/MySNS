CREATE DATABASE IF NOT EXISTS mysns
DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use mysns;

Create table if  not exists user(
	id varchar(32) primary key comment "email",
	password varchar(32) ,
	name varchar(64) ,
	nick varchar(64),
	ts timestamp default current_timestamp
);

create table if not exists feed(
	no INT unsigned auto_increment primary key comment "sequence",
	id varchar(32) comment "same as that of table 'user'",
	content varchar(4096),
	ts timestamp default current_timestamp
);
