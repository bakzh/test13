drop table product;
drop SEQUENCE product_pid_SEQ;

create SEQUENCE product_pid_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;
create table product (
  pid   NUMBER  (8)  PRIMARY Key,
  pname VARCHAR2(40) NOT NULL,
  count NUMBER(3)    NOT NULL,
  price NUMBER(30)   NOT NULL
);

insert into product VALUES(product_pid_SEQ.nextval,'컴퓨터',3,3000000);

select * from product where pid = 1;

update product set pname = '마우스', count = 100, price= 100000 where pid = 1;

delete product where pid = 1;

select * from product;