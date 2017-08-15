--使用存储过程执行秒杀
DELIMITER $$ -- console;转换为$$；定义换行符：表示

-- 定义存储过程
-- 参数：in 输入参数；out 输出参数
-- row_count():返回上一条修改类型sql（delete,insert,update）的影响行数。
-- row_count():0:未修改数据；>0：表示修改数据的行数；<0:sql错误/未执行修改sql。
CREATE PROCEDURE execute_seckill(in v_seckill_id bigint,in v_phone bigint,
												in v_kill_time timestamp,out r_result int) 
BEGIN
    DECLARE insert_count INT DEFAULT 0;
    
    START TRANSACTION ;
    
    INSERT ignore success_killed(seckill_id,user_phone,create_time) 
    VALUES(v_seckill_id,v_phone,v_kill_time); -- 先插入购买明细
    
    SELECT ROW_COUNT() INTO insert_count;
    IF(insert_count = 0) THEN
      ROLLBACK ;
      SET r_result = -1;   -- 重复秒杀
    ELSEIF(insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = -2;   -- 内部错误
    ELSE   -- 已经插入购买明细，接下来要减少库存
      update seckill 
      set number = number -1 
      WHERE seckill_id = v_seckill_id 
      		AND start_time < v_kill_time 
      		AND end_time > v_kill_time 
      		AND number > 0;
      
      select ROW_COUNT() INTO insert_count;
      IF (insert_count = 0)  THEN
        ROLLBACK ;
        SET r_result = 0;   -- 库存没有了，代表秒杀已经关闭
      ELSEIF (insert_count < 0) THEN
        ROLLBACK ;
        SET r_result = -2;   -- 内部错误
      ELSE
        COMMIT ;    -- 秒杀成功，事务提交
        SET  r_result = 1;   -- 秒杀成功返回值为1
      END IF;
    END IF;
  END
$$

-- 测试
DELIMITER ;-- 把DELIMITER重新定义还原成分号；

SET @r_result = -3;
-- 执行存储过程
CALL execute_seckill(1003,18864598658,now(),@r_result);
-- 获取结果
select @r_result;


drop procedure execute_seckill; -- 删除存储过程

--存储过程
--1：存储过程优化：事务行级锁持有的时间
--2：不要过度依赖存储过程
--3：简单的逻辑可以应用存储过程
--4：QPS一个秒杀单可以接近6000/qps
