package com.nhi.bookstore.serviceimpl;

import com.nhi.bookstore.exceptions.NotFoundException;
import com.nhi.bookstore.model.Book;
import com.nhi.bookstore.model.Comment;
import com.nhi.bookstore.model.CommentDTO;
import com.nhi.bookstore.model.User;
import com.nhi.bookstore.repositories.BookRepository;
import com.nhi.bookstore.repositories.CommentRepository;
import com.nhi.bookstore.repositories.UserRepository;
import com.nhi.bookstore.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class CommentService implements ICommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<CommentDTO> getLisCommentByBook(int id) {
        Book book= bookRepository.findById(id);
        List<Comment> comments= commentRepository.findByBook(book);
        List<CommentDTO> commentDTOS=new ArrayList<>();
        CommentDTO commentDTO=null;
        for(Comment comment:comments){
            commentDTO=new CommentDTO();
            commentDTO.setMessage(comment.getMessage());
            commentDTO.setDayCreate(comment.getDayCreated());
            commentDTO.setDayUpdate(comment.getDayUpdated());
            commentDTO.setUser(comment.getUser().getUserName());
            commentDTOS.add(commentDTO);
        }
        int a=7;
        return commentDTOS;
    }

    @Override
    public void createComment(int id, Comment comment) {
        Book book= bookRepository.findById(id);
        if(book==null){
            throw new NotFoundException("Book not found");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        comment.setBook(book);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date=formatter.format(calendar.getTime());
        comment.setDayCreated(date);
        comment.setDayUpdated(date);
        User user= userRepository.findByUserName(userName);
        comment.setUser(user);
        commentRepository.save(comment);
    }
}
