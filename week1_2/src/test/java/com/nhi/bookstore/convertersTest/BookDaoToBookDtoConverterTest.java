package com.nhi.bookstore.convertersTest;

import com.nhi.bookstore.convertersTest.bases.Converter;
import com.nhi.bookstore.model.Book;
import com.nhi.bookstore.model.BookDTO;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class BookDaoToBookDtoConverterTest {
    @Autowired
    private Converter<Book, BookDTO> bookDaoToBookDtoConverter;

    @Test
    public void test_Convert(){
        Book book = new Book();
        book.setName("Book name");
        book.setId(13);
        book.setYear(2010);

        BookDTO bookDTO = bookDaoToBookDtoConverter.convert(book);

        assertEquals(bookDTO.getName(), "Book name");
        assertEquals(bookDTO.getId(), 13);
        assertEquals(bookDTO.getYear(), 2010);
    }
}
