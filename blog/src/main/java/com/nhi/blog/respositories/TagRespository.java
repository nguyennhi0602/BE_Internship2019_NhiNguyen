package com.nhi.blog.respositories;

import com.nhi.blog.model.Comment;
import com.nhi.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRespository extends JpaRepository<Tag,Integer> {
    Tag findByNameContaining(String name);

    @Query(value = "SELECT * FROM tags_posts left join tags on tags_posts.tag_id=tags.id  WHERE post_id = ?1",nativeQuery = true)
    List<Tag> findByPostID(int id);
}
