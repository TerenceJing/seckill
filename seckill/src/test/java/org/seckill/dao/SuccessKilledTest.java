package org.seckill.dao;

import javax.annotation.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//2:告诉Junit Spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledTest {
	
	@Resource
	private SuccessKilledDao successKilledDao;	
	
	/*
	 * inserCount=0:已经插入相同记录
	 * inserCount=1:当前执行操作插入了一条记录
	 */
	@Test
    public void insertSuccessKilledTest() throws Exception 	{

        long seckillId=1002;
        long userPhone=13475191898L;
        int insertCount=successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("insertCount="+insertCount);
    }
	
	@Test
	public void queryByIdWithSeckill() throws Exception
	{
		long seckillId=1002;
        long userPhone=13475191898L;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
	}
}
