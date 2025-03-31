CREATE TABLE member (
    id BIGINT identity(1,1)NOT NULL PRIMARY KEY,
    login_id VARCHAR(256) NOT NULL,
    password VARCHAR(80) NOT NULL,
)

DROP TABLE member;

ALTER TABLE member add role varchar(30);

exec sp_help member;

insert into member(login_id, password)
values ('these9907', 'star8903');

select *
from member;
