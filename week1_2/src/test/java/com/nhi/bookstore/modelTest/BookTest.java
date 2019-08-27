package com.nhi.bookstore.modelTest;



import com.nhi.bookstore.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.Validator;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class BookTest {

    @Autowired
    private Validator validator;

    @Test
    public void test_book_OK(){
        Book book = new Book();
        book.setName("Hello");
        book.setYear(2010);
        assertTrue(validator.validate(book).isEmpty());
    }

    @Test
    public void test_book_year_under_1990(){
        Book book = new Book();
        book.setName("Hello");
        book.setYear(1989);
        assertFalse(validator.validate(book).isEmpty());
    }

    @Test
    public void test_book_year_above_2100(){
        Book book = new Book();
        book.setName("Hello");
        book.setYear(2101);
        assertFalse(validator.validate(book).isEmpty());
    }

    @Test
    public void test_book_invalid_name(){
        Book book = new Book();
        book.setName("");
        book.setYear(2000);
        assertFalse(validator.validate(book).isEmpty());
    }
}
