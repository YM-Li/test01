package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.HashMap;
import java.util.List;

/*
* 持久层接口
* */
public interface CheckGroupDao {

    //往检查组表保存
    void add(CheckGroup checkGroup);


    //往中间表插入数据方法
    void setCheckGroupAndCheckItem(HashMap map);


    //检查组分页
    Page<CheckGroup> selectByCondition(String queryString);

    //根据检查组id查询检查组检查项中间表
    int findGroupItemByCheckGroupId(Integer checkgroupId);

    //根据检查组id查询套餐检查组中间表
    int findSetmealGroupByCheckGroupId(Integer checkgroupId);

    //根据检查组id删除检查组数据
    void deleteById(Integer checkgroupId);

    //根据检查组id查询检查组
    CheckGroup findById(Integer checkgroupId);

    //根据检查组id查询关联的检查项ids
    List<Integer> findItemIdsByGroupId(Integer checkgroupId);

    //往检查组表编辑
    void edit(CheckGroup checkGroup);

    //根据检查组id删除中间表关系
    void deleteRsByGroupId(Integer id);

    //查询检查组列表数据
    List<CheckGroup> findAll();
}
