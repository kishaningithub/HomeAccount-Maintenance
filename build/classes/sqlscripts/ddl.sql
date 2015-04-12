drop table dailyaccount;
drop table income_expenditure_type;
drop sequence genTypeCode;
create table income_expenditure_type(in_or_ex varchar2(25),in_ex_code varchar2(5) primary key,in_ex_value varchar2(25));
alter table income_expenditure_type add constraint in_ex_check check('i','e');
create table dailyaccount(income_expenditure_date date,
                          in_or_ex_code constraint fk_in_ex_code references income_expenditure_type(in_ex_code),
                          amount number(37,2));
       
insert into dailyaccount(income_expenditure_date,in_or_ex_code,amount) values('23-May-2012','29','78');
select * from dailyaccount;
select * from INCOME_EXPENDITURE_TYPE;
create sequence genTypeCode  increment by 1 minvalue 1 start with 1 nomaxvalue nocycle;
select to_date('235672012') from dual
select d.income_expenditure_date,t.in_or_ex,t.in_ex_value,d.amount
from DAILYACCOUNT d,income_expenditure_type t where d.in_or_ex_code=t.in_ex_code
;
drop table userdata;

select * from userdata;
desc userdata;
select * from ClarificationMatrix;
select * from ClarificationMatrix_UserData