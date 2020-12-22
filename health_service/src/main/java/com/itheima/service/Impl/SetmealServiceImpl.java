package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/*
* 套餐服务实现类
* */
@Service(interfaceClass = SetmealService.class) //指定接口类型
@Transactional  //事务注解
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.往套餐表保存
        setmealDao.add(setmeal);
        //2.获取套餐id(selectKey)
        Integer setmealId = setmeal.getId();
        //3.往中间表插入数据（检查组id\套餐id）
        setCheckGroupAndSetmeal(setmealId,checkgroupIds);
    }

    //套餐分页
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //使用分页插件PageHelper
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page =setmealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //根据套餐id删除套餐表数据
    @Override
    public void deleteById(Integer setmealId) {
        //1.根据套餐id查询中间表记录是否存在
        int count = setmealDao.findSetmealGroupBySetmealId(setmealId);
        //2.关系存在，直接抛出异常
        if(count>0){
            throw new RuntimeException(MessageConstant.DELETE_SETMEALGROUP_FAIL);
        }
        //3.删除之前 根据套餐id查询套餐对象  得到套餐名称
        Setmeal setmeal = setmealDao.findById(setmealId);
        String img = setmeal.getImg();

        //4.不存在，根据套餐id删除套餐表数据
        setmealDao.deleteById(setmealId);
        //5.调用七牛云接口删除
        QiniuUtils.deleteFileFromQiniu(img);

    }


    //根据套餐id查询套餐表数据
    @Override
    public Setmeal findById(Integer setmealId) {
        return setmealDao.findById(setmealId);
    }


    //根据套餐id查询检查组id
    @Override
    public List<Integer> findGroupIdsBySetmealId(Integer setmealId) {
        return setmealDao.findGroupIdsBySetmealId(setmealId);
    }


    //编辑套餐
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.先编辑套餐表
        setmealDao.edit(setmeal);
        //2.获取套餐表主键id
        Integer setmealId = setmeal.getId();
        //3.根据套餐id删除套餐检查组中间表数据
        setmealDao.deleteRsBySetmealId(setmealId);
        //4.往套餐检查组中间插入数据（套餐id、检查组ids）
        setCheckGroupAndSetmeal(setmealId,checkgroupIds);
    }


    //往中间表插入数据方法
    private void setCheckGroupAndSetmeal(Integer setmealId, Integer[] checkgroupIds) {
        //先判断checkItemIds不为空null
        if(checkgroupIds != null && checkgroupIds.length>0){
            for (Integer groupId : checkgroupIds) {
                HashMap map = new HashMap();
                map.put("groupId",groupId);
                map.put("setmealId",setmealId);
                setmealDao.setCheckGroupAndSetmeal(map);
            }
        }
    }
}
