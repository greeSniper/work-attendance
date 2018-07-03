package com.tangzhe.attend.service;

import com.tangzhe.attend.dao.AttendMapper;
import com.tangzhe.attend.entity.Attend;
import com.tangzhe.attend.entity.AttendExample;
import com.tangzhe.attend.vo.QueryCondition;
import com.tangzhe.common.page.PageQueryBean;
import com.tangzhe.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * 考勤业务层
 */
@Service("attendService")
public class AttendServiceImpl implements AttendService {

    /**
     * 中午十二点 判定上下午
     */
    private static final int NOON_HOUR = 12 ;
    private static final int NOON_MINUTE = 00 ;

    private Log log = LogFactory.getLog(AttendServiceImpl.class);

    @Autowired
    private AttendMapper attendMapper;

    /**
     * 插入一条签到记录
     */
    public void signAttend(Attend attend) throws Exception {
        try {
            //今天日期
            Date today = new Date();
            //设置今天日期
            attend.setAttendDate(today);
            //设置今天星期几
            attend.setAttendWeek((byte) DateUtils.getTodayWeek());

            //中午12点，在此时间之前打卡为上午打卡，之后打卡为下午打卡
            Date noon = DateUtils.getDate(NOON_HOUR,NOON_MINUTE);

            //调用映射查询今天此人有没有打卡记录
            AttendExample example = new AttendExample();
            AttendExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(attend.getUserId());
            criteria.andAttendDateEqualTo(today);
            List<Attend> list = attendMapper.selectByExample(example);

            //判断此人今天有无打过卡
            if(list!=null && list.size()>0) {
                //此人今天已经打过卡
                //获取今天打卡记录
                Attend todayRecord = list.get(0);

                //判断上午还是下午打卡
                if(today.compareTo(noon) <= 0) {
                    //上午打卡
                    //因为今天上午已经打过卡，所以直接返回
                    return;
                } else {
                    //下午打卡
                    //设置下午打卡时间
                    todayRecord.setAttendEvening(today);
                    //更新今天打卡记录
                    attendMapper.updateByPrimaryKeySelective(todayRecord);
                }
            } else {
                //此人今天还没有打卡
                //判断上午还是下午打卡
                if(today.compareTo(noon) <= 0) {
                    //上午打卡
                    attend.setAttendMorning(today);
                } else {
                    //下午打卡
                    attend.setAttendEvening(today);
                }
                //设置打卡状态
                attend.setAttendStatus((byte) 1);
                //调用映射插入打卡记录
                attendMapper.insertSelective(attend);
            }

        } catch (Exception e) {
            log.error("用户签到异常", e);
            throw e;
        }
    }

    /**
     * 分页查询考勤数据
     */
    public PageQueryBean listAttend(QueryCondition condition) {
        //条件查询分页总条数
        int count = attendMapper.findCountByCondition(condition);

        PageQueryBean page = new PageQueryBean();
        //如果查询到总条数
        if(count > 0) {
            page.setCurrentPage(condition.getCurrentPage());
            page.setPageSize(condition.getPageSize());
            page.setTotalRows(count);

            //条件查询分页数据
            List<Attend> list = attendMapper.findListByCondition(condition);
            page.setItems(list);
        }

        //如果有记录 才去查询分页数据 没有相关记录数目 没必要去查分页数据
        return page;
    }
}




















