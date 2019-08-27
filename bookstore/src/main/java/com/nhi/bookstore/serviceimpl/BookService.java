package com.nhi.bookstore.serviceimpl;

import com.nhi.bookstore.exceptions.BadRequestException;
import com.nhi.bookstore.exceptions.NotFoundException;
import com.nhi.bookstore.model.Book;
import com.nhi.bookstore.model.User;
import com.nhi.bookstore.repositories.BookRepository;
import com.nhi.bookstore.repositories.UserRepository;
import com.nhi.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service("bookService")
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Book> getAllBookValid() {
        return bookRepository.findByEnable(1);
    }

    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getListBookByUser(int userID) {
        User user= userRepository.findById(userID);
        return bookRepository.findByUser(user);
    }

    @Override
    public List<Book> deleteBook(int id) {
        if(bookRepository.findById(id)==null){
            throw new NotFoundException("Book isn't exist");
        }
        bookRepository.deleteById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user= userRepository.findByUserName(userName);
        return bookRepository.findByUser(user);
    }

    @Override
    public Book editBook(int id,Book book) {
        Book book1= bookRepository.findById(id);
        if(book1==null ){
            throw new NotFoundException("Book is exist!");
        }
        if(book.getTitle().isEmpty() || book.getAuthor().isEmpty() ||book.getDescription().isEmpty()){
            throw new BadRequestException("Not enough information!");
        }
            book1.setTitle(book.getTitle());
            book1.setAuthor(book.getAuthor());
            book1.setDescription(book.getDescription());
            book1.setEnable(0);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
            String date=formatter.format(calendar.getTime());
            book1.setDayUpdate(date);
            bookRepository.save(book1);
            return book1;
    }

    @Override
    public List<Book> getListBookIsNotAccept() {
        return bookRepository.findByEnable(0);
    }

    @Override
    public List<Book> acceptBook(int id) {
        Book book= bookRepository.findById(id);
        if(book==null){
            throw new NotFoundException("Book not found");
        }
        book.setEnable(1);
        bookRepository.save(book);
        return bookRepository.findByEnable(0);
    }

    @Override
    public List<Book> pagingBook(int pageNumber, int amountBook,String content) {
        List<Book> result= bookRepository.pagingBook(amountBook,(pageNumber-1)*amountBook,content);
        return result;
    }

    @Override
    public Book createBook(Book book) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
        String date=formatter.format(calendar.getTime());
        book.setDayCreate(date);
        book.setDayUpdate(date);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user= userRepository.findByUserName(userName);
        book.setUser(user);
        book.setEnable(0);
        bookRepository.save(book);
        return book;
    }

    @Override
    public List<Book> deleteBookUnable(int id) {
        if(bookRepository.findById(id)==null){
            throw new NotFoundException("Book isn't exist");
        }
        bookRepository.deleteById(id);
        return bookRepository.findByEnable(0);
    }

    @Override
    public List<Book> searchBook(String content) {
        return bookRepository.searchBook(content);
    }
}
