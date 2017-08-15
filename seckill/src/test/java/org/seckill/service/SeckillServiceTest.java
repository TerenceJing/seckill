
package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	
	private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillListTest() throws Exception {
        List<Seckill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
        for(Seckill sk:list)
        {
        	System.out.println(sk);
        }
        

    }

    @Test
    public void getByIdTest() throws Exception {

        long seckillId=1000;
        Seckill seckill=seckillService.getById(seckillId);
        logger.info("seckill={}",seckill);
        System.out.println(seckill);
    }
    
    @Test //完整的逻辑代码集成测试
    public void testExportSeckillUrl() throws Exception{
    	long id=1001;
    	Exposer exposer=seckillService.exportSeckillUrl(id);
    	logger.info("exposer={}",exposer);
    	//判断秒杀是否开启，如果开启则保留地址和盐值md5开始秒杀;
    	if (exposer.isExposed())
        {
    		//秒杀开启，则记录账户开始秒杀
    		System.out.println(exposer);

            long userPhone=13476191876L;
            String md5=exposer.getMd5();

            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
                System.out.println(seckillExecution);
            }catch (RepeatKillException e)
            {
                e.printStackTrace();
            }catch (SeckillCloseException e1)
            {
                e1.printStackTrace();
            }
        }else {
            //秒杀未开启
            System.out.println(exposer);
        }
    }
    
    @Test //单独的执行测试
    public void testExecuteSeckill() throws Exception{
    	long id=1001;
    	long phone=1501936156052L;
    	String md5="1da8af7e7ad6829f9eb2e6f18cb45225";
    	
    	try {
    		SeckillExecution execution=seckillService.executeSeckill(id, phone, md5);
        	logger.info("result={}",execution);
        	System.out.println(execution);
        }catch (RepeatKillException e)
        {
            e.printStackTrace();
        }catch (SeckillCloseException e1)
        {
            e1.printStackTrace();
        }
    }

}
