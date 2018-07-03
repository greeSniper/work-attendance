package com.tangzhe.attend.service;

import com.tangzhe.attend.entity.Attend;
import com.tangzhe.attend.vo.QueryCondition;
import com.tangzhe.common.page.PageQueryBean;

public interface AttendService {

    void signAttend(Attend attend) throws Exception;

    PageQueryBean listAttend(QueryCondition condition);

}
