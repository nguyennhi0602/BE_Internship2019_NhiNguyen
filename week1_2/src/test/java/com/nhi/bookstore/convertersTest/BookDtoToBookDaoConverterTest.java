package com.nhi.bookstore.convertersTest;

import com.nhi.bookstore.convertersTest.bases.Converter;
import com.nhi.bookstore.model.Book;
import com.nhi.bookstore.model.BookDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class BookDtoToBookDaoConverterTest {
    @Autowired
    private Converter<BookDTO, Book> bookDtoToBookDaoConverter;

    @Test
    public void test_convert(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("Book");
        bookDTO.setYear(2010);
        bookDTO.setId(12);

        Book book = bookDtoToBookDaoConverter.convert(bookDTO);
        assertEquals(book.getId(), 12);
        assertEquals(book.getName(), "Book");
        assertEquals(book.getYear(), 2010);

    }
}
