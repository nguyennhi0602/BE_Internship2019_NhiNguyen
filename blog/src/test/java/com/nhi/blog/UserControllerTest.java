package com.nhi.blog;

import com.nhi.blog.model.Post;
import com.nhi.blog.model.Tag;
import com.nhi.blog.model.User;
import com.nhi.blog.respositories.PostResppository;
import com.nhi.blog.respositories.TagRespository;
import com.nhi.blog.respositories.UserRespository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class UserControllerTest {
    @Autowired
    private PostResppository postResppository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private TagRespository tagRespository;


    @Test
    public void test_createPost(){

        User user=userRespository.findByUserName("nhinguyen");
        Post post=new Post();
       // post = postResppository.save(new Post("post1","huhuhuhu",user));
        post.setTags(new ArrayList<>());
        post.getTags().add(tagRespository.findByNameContaining("tag1"));
        post.setTitle("post2");
        post.setContent("nnnnnnnnnnnnnnn");
        post.setUser(user);
        //List<Tag> tags=tagRespository.findAll();
        //model.addAttribute("listtag",tags);
//        post.setTitle("post1");
//        post.setContent("dmmmmmmmmmmmmmmmmmmmmmmmmmm");
//        post.setUser(user);
        postResppository.save(post);
    }
}
