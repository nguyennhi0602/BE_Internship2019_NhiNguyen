package com.nhi.bookstore.repositories;

import com.nhi.bookstore.model.Book;
import com.nhi.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findById(int id);

    List<Book> findByEnable(int enable);

    List<Book> findByUser(User user);

    @Query(value = "SELECT * FROM book where enable=1 and (author like CONCAT('%', ?3, '%') or description like CONCAT('%', ?3, '%') or title like CONCAT('%', ?3, '%')) ORDER BY id ASC LIMIT ?1 OFFSET ?2 ",nativeQuery = true)
    List<Book> pagingBook(int a,int b,String content);

    @Query(value = "SELECT * FROM book where enable=1 and (author like CONCAT('%', ?1, '%') or description like CONCAT('%', ?1, '%') or title like CONCAT('%', ?1, '%'))",nativeQuery = true)
    List<Book> searchBook( String content);
}
