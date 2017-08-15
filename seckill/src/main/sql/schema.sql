--数据库初始化脚本
--为什么手写DDL
--记录每次上线的DDL修改
--上线V1.1
ALTER TABLE seckill
DROP INDEX idx_create_time,
ADD index idx_c_s(start_time,create_time);
--上线v1.2
--ddl


--创建数据库
CREATE database seckill;
--使用数据库
use seckill;
--创建秒杀库存表 ，只有InnoDB数据库支持事务
CREATE TABLE seckill(
seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
name VARCHAR(120) NOT NULL COMMENT '商品名称',
number int NOT NULL COMMENT '库存数量',
/*默认时间戳要在自定义的时间前面，否则会出错*/
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
start_time TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
end_time TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--初始化数据
insert into seckill(name,number,start_time,end_time)
values('1000元秒杀iphone6',100,'2016-07-07 00:00:00','2016-07-08 00:00:00'),
values('500元秒杀ipad2',200,'2016-07-07 00:00:00','2016-07-08 00:00:00'),
values('400元秒杀小米note4',300,'2016-07-07 00:00:00','2016-07-08 00:00:00'),
values('200元秒杀红米note',300,'2016-07-07 00:00:00','2016-07-08 00:00:00');

--秒杀成功明细表
--用户登录认证相关信息
create table success_killed(
seckill_id BIGINT not null COMMENT '秒杀商品id',
user_phone BIGINT not null COMMENT '用户手机号',
state tinyint not null DEFAULT -1 COMMENT '状态标识：-1：无效  0：成功  1：已付款',
create_time timestamp not null COMMENT '创建时间',
PRIMARY key(seckill_id,user_phone),/*联合主键*/
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';



--连接数据库控制台
mysql -uroot -proot


