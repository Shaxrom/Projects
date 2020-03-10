begin;

drop schema if exists chat cascade;
create schema chat;

drop table if exists chat.user;
create table chat.user (
  id    serial             not null,
  login varchar(20) unique not null,

  primary key (id)
);
commit;
