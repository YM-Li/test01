package com.itheima;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/*
* 清理垃圾图片测试类
* */
//@ContextConfiguration("classpath:spring-redis.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
public class ClearImageTest {

    @Autowired
    private JedisPool jedisPool;

    //@Test
    public void testClearImg(){
        //sdiff方法 传入两个key 得到两个集合的差集 key1：集合1  key2：集合2
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //非空判断
        if(sdiff != null && sdiff.size()>0){
            //遍历集合 得到每一个图片名称
            for (String filename : sdiff) {
                System.out.println("filename********" + filename);
                //调用七牛云删除照片
                QiniuUtils.deleteFileFromQiniu(filename);
                //删除redis中的记录  集合1中value的值
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
                System.out.println("******删除成功了******");
            }

        }

    }


}
