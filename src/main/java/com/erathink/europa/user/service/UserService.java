package com.erathink.europa.user.service;

import com.erathink.europa.user.dao.UserDAO;
import com.erathink.europa.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fengyun on 2017/12/17.
 */

@Component
public class UserService {

    @Autowired
    UserDAO userDAO;

    public UserEntity getById(String id){
        return userDAO.findOne(id);
    }

    public UserEntity getByUserNameAndPW(String userName,String pw){
        return userDAO.findByUserNameAndPassword(userName,pw);
    }
}
