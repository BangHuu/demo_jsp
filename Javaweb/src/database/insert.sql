
insert into role(code,name) values('ADMIN','ADMIN');
insert into role(code,name) values('USER','USER');

insert into user(username,password,fullname,status, roleid) values('admin','123456','admin',1,1);
insert into user(username,password,fullname,status, roleid) values('nguyenvana','123456','nguyen van a',1,2);
insert into user(username,password,fullname,status, roleid) values('nguyenvanb','123456','nguyen van b',1,2);

insert into category(name,code) values('the-thao',	'Thể thao');
insert into category(name,code) values('the-gioi',	'Thế giới');
insert into category(name,code) values('chinh-tri',	'Chính trị ');
insert into category(name,code) values('thoi-su',	'Thời sự');
insert into category(name,code) values('goc-nhin',	'Góc nhìn');