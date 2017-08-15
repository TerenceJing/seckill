package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Terence
 *
 */

public interface SeckillDao {
	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return 如果影响行数>1,表示更新行数
	 */
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
	
	/**
	 * 根据id查询秒杀库存
	 * @param seckillId
	 * @return 插入的行数
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量查询秒杀列表
	 * @param offset
	 * @param limit
	 * @return
	 * 唯一形参自动赋值
	 * 当有多个参数的时候要指定实际的形参名称赋值，不然找不到对应值，因为Java并没有保存形参的记录
	 * java在运行的时候会把List<Seckill> queryAll(int offset,int limit);中的参数变成这样:queryAll(int arg0,int arg1),这样我们就没有办法去传递多个参数
	 */
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);

	
	/**
	 * 秒杀操作优化：
	 *  使用存储过程执行秒杀
	 * @param paramMap
	 */
	void killByProcedure(Map<String,Object> paramMap);
}
