package com.nhi.bookstore.repositories;

import com.nhi.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);

    User findById(int id);

}
