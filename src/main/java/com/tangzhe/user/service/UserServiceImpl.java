package com.tangzhe.user.service;

import com.tangzhe.common.utils.MD5Utils;
import com.tangzhe.user.dao.UserMapper;
import com.tangzhe.user.entity.User;
import com.tangzhe.user.entity.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 用户业务层
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通过id查询用户
     */
    public User findById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    /**
     * 测试插入用户，事务是否正确执行
     */
    @Transactional
    public void testCreate(User user1, User user2) {
        userMapper.insertSelective(user1);
        //插入主键冲突的一条数据，测试事务
        userMapper.insertSelective(user2);
    }

    /**
     * 插入用户
     */
    @Transactional
    public void create(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setPassword(MD5Utils.encryptPassword(user.getPassword()));
        userMapper.insertSelective(user);
    }

    /**
     * 通过用户登录名查询
     */
    public User findByUsername(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

}
