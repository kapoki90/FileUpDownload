drop table fileboard;
create table fileboard (
    idx int not null primary key,
    ref int not null,
    lev int not null,
    seq int not null,
 name varchar2(50) not null,
 pwd varchar2(50) not null,
 title varchar2(100) not null,
 content varchar2(3000) not null,
 filename varchar2(1000) not null,
  fileExtension varchar2(50) not null,
 filesize number not null,
  wdate TIMESTAMP (6) DEFAULT sysdate
);
wdate TIMESTAMP (6) DEFAULT sysdate
drop sequence fileboard_idx_seq;
create sequence fileboard_idx_seq;
create table fileboard (
 name varchar2(50) not null,
 pwd varchar2(50) not null,
 title varchar2(100) not null,
 content varchar2(3000) not null,
 filename varchar2(1000) not null,
 filesize number not null
);
select * from fileboard; 