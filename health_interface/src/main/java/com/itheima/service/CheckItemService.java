package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

import java.util.List;

/*
* 检查项服务接口层
* */
public interface CheckItemService {
    /*
     * 查询所有检查项列表数据
     * */
    List<CheckItem> findAll();

    //新增检查项
    void add(CheckItem checkItem);

    //检查项分页
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    //检查项删除
    void deleteById(Integer checkItemId);

    //根据id查询检查项
    CheckItem findById(Integer checkItemId);

    //编辑检查项
    void edit(CheckItem checkItem);
}
