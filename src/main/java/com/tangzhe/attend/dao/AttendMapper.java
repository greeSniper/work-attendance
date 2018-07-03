package com.tangzhe.attend.dao;

import com.tangzhe.attend.entity.Attend;
import com.tangzhe.attend.entity.AttendExample;
import java.util.List;
import com.tangzhe.attend.vo.QueryCondition;
import org.apache.ibatis.annotations.Param;

public interface AttendMapper {
    int countByExample(AttendExample example);

    int deleteByExample(AttendExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Attend record);

    int insertSelective(Attend record);

    List<Attend> selectByExample(AttendExample example);

    Attend selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Attend record, @Param("example") AttendExample example);

    int updateByExample(@Param("record") Attend record, @Param("example") AttendExample example);

    int updateByPrimaryKeySelective(Attend record);

    int updateByPrimaryKey(Attend record);

    int findCountByCondition(QueryCondition condition);

    List<Attend> findListByCondition(QueryCondition condition);
}