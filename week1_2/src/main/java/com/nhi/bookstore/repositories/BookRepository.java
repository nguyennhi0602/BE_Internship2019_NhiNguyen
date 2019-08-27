package com.nhi.bookstore.repositories;

import com.nhi.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Iterable<Book> findByNameContaining(String name);
}
