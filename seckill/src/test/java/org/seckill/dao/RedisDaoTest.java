package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.cache.RedisDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
	private long id=1001;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testSeckill() throws Exception{
		//get and put
		Seckill seckill=redisDao.getSeckill(id);//从缓存中获取
		
		if(seckill==null) //缓冲中没有则查询
		{
			seckill=seckillDao.queryById(id);//
			if(seckill!=null)
			{
				String result=redisDao.putSeckill(seckill); //缓存序列化对象
				System.out.println(result);
				seckill=redisDao.getSeckill(id);
				System.out.println(seckill);
				
			}
		}
		else
		{
			System.out.println("从缓存中获取成功："+seckill);
		}
	}

}
