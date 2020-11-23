---------- 스프링 게시판 -----------

show user;

create table spring_test
(no         number
,name       varchar2(100)
,writeday   date default sysdate
);

insert into spring_test(no, name, writeday)
		values (101, '김민성', default)

select *
from spring_test;

delete from spring_test;    
commit;