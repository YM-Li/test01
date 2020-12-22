package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


/*
* 套餐控制层
* */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //图片上传
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result upload(MultipartFile imgFile){

        try {
            //1.自定义文件名称，保证文件名唯一
            String originalFilename = imgFile.getOriginalFilename();//原始文件名称 1.jpg
            //获取文件后缀
            String suffix = originalFilename.substring(originalFilename.indexOf("."));
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString();
            String newFileName = fileName+suffix;
            //2.调用七牛云工具类上传图片
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),newFileName);
            //4.将操作记录写到redis  集合1
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
            //3.返回图片名称
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

    //新增套餐
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
            //将操作记录写到redis  集合2：已成功新增的图片
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }

    //套餐分页
    @RequestMapping(value = "/findPage",method = RequestMethod.POST)
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return setmealService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据套餐id删除套餐表数据
    @RequestMapping(value = "/deleteById",method = RequestMethod.GET)
    public Result deleteById(Integer setmealId){
        try {
            setmealService.deleteById(setmealId);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }

    }

    //根据套餐id查询套餐表数据
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Result findById(Integer setmealId){
        try {
            Setmeal setmeal = setmealService.findById(setmealId);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }

    //根据套餐id查询检查组id
    @RequestMapping(value = "/findGroupIdsBySetmealId",method = RequestMethod.GET)
    public List<Integer> findGroupIdsBySetmealId(Integer setmealId){
        try {
            return setmealService.findGroupIdsBySetmealId(setmealId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //编辑套餐
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setmealService.edit(setmeal,checkgroupIds);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }

    }
}
