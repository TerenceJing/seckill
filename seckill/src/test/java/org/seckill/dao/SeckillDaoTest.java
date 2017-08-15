package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.*;

/**
 * 配置Spring和Junit整合，Junit启动时加载SPringIOC容器
 * spring-test,junit:spring测试的依赖
 * 1:RunWith:Junit本身需要的依赖
 * @author Terence
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 2:告诉Junit Spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	//3:注入Dao实现类依赖 --会自动去Spring容器中查找seckillDao的实现类注入到单元测试类
	@Resource
	private SeckillDao seckillDao;
	
    @Test
    public void testQueryById() throws Exception {
    	long id=1000;
    	Seckill seckill=seckillDao.queryById(id);
    	System.out.println(seckill.getName());
    	System.out.println(seckill);
    	/*
    	 * 1000元秒杀iphone6
    	 * Seckill [seckillId=1000, name=1000元秒杀iphone6, number=100, createTime=Thu Aug 03 09:20:36 CST 2017, startTime=Thu Jul 07 00:00:00 CST 2016, endTime=Fri Jul 08 00:00:00 CST 2016]
    	 */
    }

    /**
     * java在运行的时候会把List<Seckill> queryAll(int offset,int limit);中的参数变成这样:queryAll(int arg0,int arg1),这样我们就没有办法去传递多个参数
     * 
     * @throws Exception
     */
    @Test
    public void queryAll() throws Exception {
    	List<Seckill> seckills=seckillDao.queryAll(0, 10);
    	for(Seckill s:seckills)
    	{
    		System.out.println(s);
    	}
    }
    
    @Test
    public void reduceNumber() throws Exception {
    	long seckillId=1000;
    	Date killTime=new Date();
    	int updateCount=seckillDao.reduceNumber(seckillId, killTime);
    	System.out.println("updateCount="+updateCount);

    }
}
