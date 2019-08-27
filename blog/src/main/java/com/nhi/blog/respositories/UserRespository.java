package com.nhi.blog.respositories;

import com.nhi.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
    User findById(int id);
}
