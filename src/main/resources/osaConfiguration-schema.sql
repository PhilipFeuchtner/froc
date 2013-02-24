drop table config if exists;


create table config (id integer identity primary key, osaName varchar(200) not null, jeeDatasource varchar(200) not null, fileBasePath varchar(200) not null );