
alter table app_secret add public_key varchar(10240) NOT NULL COLLATE utf8_bin COMMENT '公钥';
alter table app_secret add private_key varchar(10240) NOT NULL COLLATE utf8_bin COMMENT '私钥';

