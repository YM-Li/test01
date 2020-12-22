package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/*
 * 检查项持久层接口
 * */
public interface CheckItemDao {

    /*
     * 查询所有检查项列表数据
     * */
    List<CheckItem> findAll();

    //新增检查项
    void add(CheckItem checkItem);

    //检查项分页查询
    Page<CheckItem> selectByCondition(String queryString);

    //先根据检查项id到中间表查询关系是否存在
    int findCountByCheckItemId(Integer checkItemId);

    //调用dao删除检查项
    void deleteById(Integer checkItemId);

    //根据id查询检查项
    CheckItem findById(Integer checkItemId);

    //编辑检查项
    void edit(CheckItem checkItem);
}
