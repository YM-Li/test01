package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
* 检查项服务接口层
* */
@Service
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;
    /*
     * 查询所有检查项列表数据
     * */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    //新增检查项
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //检查项分页,使用分页插件
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //1.设置分页参数
        PageHelper.startPage(currentPage,pageSize);
        //2.需要分页的查询数据（第一步跟第二步之间不能有任何代其他码）
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        //3.返回结果
        return new PageResult(page.getTotal(),page.getResult());
    }

    //检查项删除
    @Override
    public void deleteById(Integer checkItemId) {
        //1.先根据检查项id到中间表查询关系是否存在
        int count = checkItemDao.findCountByCheckItemId(checkItemId);
        //2.有关系，返回错误提示“检查组跟检查项存在关系，无法删除”
        if(count>0){
            throw new RuntimeException(MessageConstant.DELETE_ITEMGROUP_FAIL);
        }
        //3.没有关系，调用dao删除检查项
        checkItemDao.deleteById(checkItemId);
    }

    //根据id查询检查项
    @Override
    public CheckItem findById(Integer checkItemId) {
        return checkItemDao.findById(checkItemId);
    }

    //编辑检查项
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }
}
