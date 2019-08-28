package com.nhi.bookstore.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerThymeleaf {

    @RequestMapping("/success")
    public String getSuccess(){
        return "success";
    }
    @RequestMapping("/homepage-user")
    public String getHomepageUser(){
        return "homepageUser";
    }

    @RequestMapping("/homepage-admin")
    public String getHomepageAdmin(){
        return "homepageAdmin";
    }

    @RequestMapping("/book-disable")
    public String getBookWaiting(){
        return "bookWaiting";
    }

    @RequestMapping("/userWaiting")
    public String getUserWaiting(){
        return "userWaiting";
    }

    @RequestMapping("/listUser")
    public String getListUser(){
        return "listUser";
    }

    @RequestMapping("/index1")
    public String getIndex1(){
        return "index1";
    }

    @RequestMapping("/index")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/my-book")
    public String getMyBook(){
        return "myBook";
    }


    @RequestMapping("/book/{id}")
    public String book(){
        return "book";
    }

    @RequestMapping("/book-user/{id}")
    public String bookUser(){
        return "bookUser";
    }

    @RequestMapping("/book-admin/{id}")
    public String bookAdmin(){
        return "bookAdmin";
    }

    @RequestMapping("/edit-book/{id}")
    public String editBook(){
        return "editBookForm";
    }
}
