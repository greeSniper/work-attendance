package com.tangzhe.user.service;

import com.tangzhe.user.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UserService {

    User findById(Long id);
    void testCreate(User user1, User user2);
    void create(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    User findByUsername(String username);

}
