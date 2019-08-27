package com.nhi.bookstore.repositoryTest;

import com.nhi.bookstore.model.User;
import com.nhi.bookstore.repositories.UserReponsitory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserReponsitory userRepository;

    @Test
    public void test_findByUsername(){
        userRepository.save(new User("abc", "First name", "Last name", "123"));

        Assert.assertNotNull(userRepository.findByUserName("abc"));
        Assert.assertNull(userRepository.findByUserName("ffff"));
    }
}
