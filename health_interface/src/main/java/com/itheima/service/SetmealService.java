package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

import java.util.List;

/*
 * 套餐服务接口
 * */
public interface SetmealService {

    //新增套餐
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    //套餐分页
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //根据套餐id删除套餐表数据
    void deleteById(Integer setmealId);

    //根据套餐id查询套餐表数据
    Setmeal findById(Integer setmealId);

    //根据套餐id查询检查组id
    List<Integer> findGroupIdsBySetmealId(Integer setmealId);

    //编辑套餐
    void edit(Setmeal setmeal, Integer[] checkgroupIds);
}
