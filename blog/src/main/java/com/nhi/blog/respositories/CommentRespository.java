package com.nhi.blog.respositories;

import com.nhi.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRespository extends JpaRepository<Comment,Integer> {

    @Query(value = "SELECT * FROM comment  WHERE post_id = ?1",nativeQuery = true)
    List<Comment> findByPostID(int id);
}
