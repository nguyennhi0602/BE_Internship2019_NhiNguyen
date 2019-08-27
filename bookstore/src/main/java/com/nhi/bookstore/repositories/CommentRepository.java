package com.nhi.bookstore.repositories;

import com.nhi.bookstore.model.Book;
import com.nhi.bookstore.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByBook(Book book);
}