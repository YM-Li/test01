package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/*
* 检查组实现类
* */
@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    //新增检查组
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        //1.往检查组表保存
        checkGroupDao.add(checkGroup);
        //2.获取检查组id(selectKey)
        Integer groupId = checkGroup.getId();
        //3.往中间表插入数据（检查组id\检查项id）
        setCheckGroupAndCheckItem(groupId,checkItemIds);
    }

    //检查组分页
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //使用分页插件PageHelper
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page =checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());

    }

    //根据检查组id删除检查组
    @Override
    public void deleteById(Integer checkgroupId) {

        //1.根据检查组id查询检查组检查项中间表
        int count = checkGroupDao.findGroupItemByCheckGroupId(checkgroupId);
        //2.关系存在，直接抛出异常
        if(count>0){
            throw new RuntimeException(MessageConstant.DELETE_ITEMGROUP_FAIL);
        }
        //3.关系不存在， 根据检查组id查询套餐检查组中间表
        int count1 = checkGroupDao.findSetmealGroupByCheckGroupId(checkgroupId);
        //4.关系存在，直接抛出异常
        if(count1>0){
            throw new RuntimeException(MessageConstant.DELETE_SETMEALGROUP_FAIL);
        }
        //5.不存在，根据检查组id删除检查组数据
        checkGroupDao.deleteById(checkgroupId);
    }


    //根据检查组id查询检查组
    @Override
    public CheckGroup findById(Integer checkgroupId) {
        return checkGroupDao.findById(checkgroupId);
    }


    //根据检查组id查询关联的检查项ids
    @Override
    public List<Integer> findItemIdsByGroupId(Integer checkgroupId) {
        return checkGroupDao.findItemIdsByGroupId(checkgroupId);
    }

    //编辑检查组
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        //1.往检查组表编辑
        checkGroupDao.edit(checkGroup);
        //2.根据检查组id删除中间表关系
        checkGroupDao.deleteRsByGroupId(checkGroup.getId());
        //3.往中间表插入数据（检查组id\检查项id）
        setCheckGroupAndCheckItem(checkGroup.getId(),checkItemIds);
    }


    //查询检查组列表数据
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    //往中间表插入数据方法
    private void setCheckGroupAndCheckItem(Integer groupId, Integer[] checkItemIds) {
        //先判断checkItemIds不为空null
        if(checkItemIds != null && checkItemIds.length>0){
            for (Integer checkItemId : checkItemIds) {
                HashMap map = new HashMap();
                map.put("groupId",groupId);
                map.put("checkItemId",checkItemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }

    }


}
