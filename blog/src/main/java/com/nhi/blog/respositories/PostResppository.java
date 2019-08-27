package com.nhi.blog.respositories;

import com.nhi.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostResppository extends JpaRepository<Post,Integer> {
        Post findById(int id);

        @Query(value = "SELECT * FROM post  WHERE user_id = ?1",nativeQuery = true)
        List<Post> findByUserID(int id);

        @Query(value = "SELECT * FROM post left join tags_posts on post.id= tags_posts.post_id WHERE tag_id =?1",nativeQuery = true)
        List<Post> findByTagID(int id);
}
