package com.nhi.bookstore.controllerTest;

import com.google.gson.Gson;
import com.nhi.bookstore.model.Book;
import com.nhi.bookstore.repositories.BookRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class BookControllerTest {
    @Autowired
    private BookRepository bookRepository;

    private Book book1;
    private Book book2;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init(){
        book1 = bookRepository.save(new Book(1,"English",2000));
        book2 = bookRepository.save(new Book(2, "Mathematics",1997));
    }

    @After
    public void destroy(){
        bookRepository.deleteAll();
    }

    @Test
    public void test_getAllBook() throws Exception{
        mockMvc.perform(get(("/api/books")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(book1.getId())))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("English")))
                .andExpect(jsonPath("$[1].id", Matchers.equalTo(book2.getId())))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo("Mathematics")));

    }


    @Test
    public void test_getBook_Found() throws Exception{
        mockMvc.perform(get("/api/books/" + book2.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.equalTo(book2.getId())))
                .andExpect(jsonPath("$.name", Matchers.equalTo(book2.getName())));
    }

    @Test
    public void test_getBook_NotFound() throws Exception{
        mockMvc.perform(get("/api/books/" + (book2.getId() + book1.getId())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteBook_NotFound() throws Exception{
        mockMvc.perform(delete("/api/books/" + (book2.getId() + book1.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteBook_Found() throws Exception{
        mockMvc.perform(delete("/api/books/" + book1.getId()))
                .andExpect(status().isOk());

        assertFalse(bookRepository.findById( book1.getId()).isPresent());
    }

    @Test
    public void test_put_Found() throws Exception{

        Gson gson = new Gson();
        String json = gson.toJson(new Book(book2.getId(), "Math",book2.getYear()));

        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        Optional<Book> book = bookRepository.findById(book2.getId());

        Assert.assertTrue(book.isPresent());
        Assert.assertEquals(book.get().getName(), "Math");
    }

    @Test
    public void test_post_ok() throws Exception{

        Gson gson = new Gson();
        String json = gson.toJson(new Book(0, "sach",1987));

        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());


        ArrayList<Book> books = (ArrayList<Book>) bookRepository.findAll();
        Book book = books.get(0);
        System.out.println(books);

        Assert.assertEquals(book.getName(), "sach");
    }
}
