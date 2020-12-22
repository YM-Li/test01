package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/*
* 检查组服务接口
* */
public interface CheckGroupService {

    //新增检查组
    void add(CheckGroup checkGroup, Integer[] checkItemIds);



    //检查组分页
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);


    //根据检查组id删除检查组
    void deleteById(Integer checkgroupId);

    //根据检查组id查询检查组
    CheckGroup findById(Integer checkgroupId);

    //根据检查组id查询关联的检查项ids
    List<Integer> findItemIdsByGroupId(Integer checkgroupId);

    //编辑检查组
    void edit(CheckGroup checkGroup, Integer[] checkItemIds);

    //查询检查组列表数据
    List<CheckGroup> findAll();
}
