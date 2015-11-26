insert into user (mobile, nickname, password) values (18612345678, 'athena', 'password');
insert into user (mobile, nickname, password) values (18612345679, 'user', 'password');
insert into user (mobile, nickname, password) values (18612345680, 'visitor', 'password');

insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_USER');
insert into role (name) values ('ROLE_VISITOR');

insert into user_roles values (1,1);
insert into user_roles values (2,2);
insert into user_roles values (3,3);
