package com.nhi.blog.controller;

import com.nhi.blog.model.Post;
import com.nhi.blog.respositories.PostResppository;
import com.nhi.blog.respositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/post")
@Secured("ROLE_MEMBER")
public class PostController {

    @Autowired
    private PostResppository postResppository;

    @Autowired
    private UserRespository userRespository;

    @RequestMapping("createPostView")
    public String getcreatePostView(){
        return "createpost";
    }

    @GetMapping
    Iterable<Post> get(){
        return postResppository.findAll();
    }

    @PostMapping("/createPost")
    void createPost(@Valid @RequestBody Post post, String id){

        post.setId(0);
        postResppository.save(post);
    }

    @PutMapping("/update/{id}")
    void updatePost(@Valid  @RequestBody Post post,@PathVariable int id){
        postResppository.save(post);
    }

    @DeleteMapping("/delete/{id}")
    void deletePost(@PathVariable int id){
        postResppository.deleteById(id);
    }
}
