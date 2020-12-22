/*
package com.itheima.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * 会员服务接口实现类
 *//*

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    //会员数量折线图
    @Override
    public Map getMemberReport() {
        //定义返回结果map
        Map rsMap = new HashMap();
        //通过日历对象 得到最一年年月数据，放到List<String>中
        List<String> months =new ArrayList<>();
        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //往前1年时间
        calendar.add(Calendar.MONTH,-12);
        for (int i = 0;i<12;i++){

            String yearMonth = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
            months.add("yearMonth");
            //每循环一次就 +1
            calendar.add(Calendar.MONTH,1);
        }

        //循环遍历查询会员表数量，放到List<Integer>
        List<Integer> memberCount = new ArrayList<>();
        //select count(*) from t_member where regTime <='2020-01-31'

        for (String month : months) {
            //循环12次
            String yearMonthDay = month + "-31";//2020-01-31'
            //查询每个月的会员数量
            Integer count = memberDao.findMemberCountBeforeDate(yearMonthDay);
            //查到之后放在memberCount里
            memberCount.add(count);

        }
        //将List<String> List<Integer>存入map中

        rsMap.put("months",months);
        rsMap.put("memberCount",memberCount);
        return rsMap;
    }
}
*/
