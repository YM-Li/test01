package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.HashMap;
import java.util.List;

/*
* 套餐持久层接口
* */
public interface SetmealDao {

    //往套餐表保存
    void add(Setmeal setmeal);

    //往中间表插入数据方法
    void setCheckGroupAndSetmeal(HashMap map);

    //套餐分页
    Page<Setmeal> selectByCondition(String queryString);

    //根据套餐id查询中间表记录是否存在
    int findSetmealGroupBySetmealId(Integer setmealId);

    //根据套餐id删除套餐表数据
    void deleteById(Integer setmealId);

    //根据套餐id查询套餐对象
    Setmeal findById(Integer setmealId);


    //根据套餐id查询检查组id
    List<Integer> findGroupIdsBySetmealId(Integer setmealId);

    //编辑套餐表
    void edit(Setmeal setmeal);

    //根据套餐id删除套餐检查组中间表数据
    void deleteRsBySetmealId(Integer setmealId);
}
