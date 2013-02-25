drop table CONFIG if exists;


create table CONFIG (ID integer identity primary key, OSANAME varchar(200) not null, JEEDATASOURCE varchar(200) not null, FILEBASEPATH varchar(200) not null );
