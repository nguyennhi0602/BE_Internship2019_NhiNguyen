package com.nhi.bookstore.controllers;

import com.nhi.bookstore.convertersTest.bases.Converter;
import com.nhi.bookstore.model.Book;
import com.nhi.bookstore.exceptions.NotFoundException;
import com.nhi.bookstore.model.BookDTO;
import com.nhi.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private Converter<BookDTO, Book> bookDTOBookConverter;

    @Autowired
    private Converter<Book, BookDTO> bookBookDTOConverter;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{id}")
    public BookDTO get(@PathVariable int id){
        Optional<Book> optionalBook = bookRepository.findById(id);

        if(optionalBook.isPresent()){
            return bookBookDTOConverter.convert(optionalBook.get());
        }

        throw new NotFoundException(String.format("Book id %d not found", id));
    }

    @GetMapping()
    public Iterable<BookDTO> get(){
        return  bookBookDTOConverter.convert(bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }


    //    @GetMapping("/find")
//    Iterable<Book> find(@RequestParam String name){
//        return bookRepository.findByNameContaining(name);
//    }
//    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id){

        if(!bookRepository.existsById(id)){
            throw new NotFoundException(String.format("Book id %d not found", id));
        }

        bookRepository.deleteById(id);
    }

    @PostMapping()
    public void post(@Valid @RequestBody BookDTO book){
        book.setId(0);
        bookRepository.save(bookDTOBookConverter.convert(book));
    }
    @PutMapping()
    void put(@RequestBody Book book) {
        bookRepository.save(book);
    }

}
