package com.nhi.blog.service;

import com.nhi.blog.dao.Login;
import com.nhi.blog.respositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserRespository userRespository;

    public boolean checkLogin(Login login){
        if(userRespository.findByUserName(login.getUsername())==null){
            return false;
        }
        return true;
    }
}
