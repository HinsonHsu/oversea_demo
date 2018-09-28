create database oversea_user;

create table user_base (
uid bigint not null primary key auto_increment,
user_name varchar(50) not null,
user_type int not null,
introduction varchar(500),
head_url varchar(200) not null,
city_id int,
address varchar(100),
user_status int not null,
create_time timestamp not null,
update_time timestamp not null,
submit_time timestamp null,
audit_time timestamp null,
auditor varchar(20)
);

create table local_account (
id bigint not null primary key auto_increment,
uid bigint not null,
email varchar(40) not null,
pwd varchar(40) not null
);

create table oauth_account (
id bigint not null primary key auto_increment,
uid bigint not null,
oauth_source int not null,
oauth_id varchar(40) not null
);