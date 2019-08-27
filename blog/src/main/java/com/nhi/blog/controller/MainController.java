package com.nhi.blog.controller;

import com.nhi.blog.configurations.Constants;
import com.nhi.blog.configurations.TokenProvider;
import com.nhi.blog.dao.Login;
import com.nhi.blog.model.*;
import com.nhi.blog.respositories.*;
import com.nhi.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
//@Secured("ROLE_MEMBER")
public class MainController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private PostResppository postResppository;

    @Autowired
    private TagRespository tagRespository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRespository commentRespository;

    @GetMapping("/login")
    public String index() {
        return "login2";
    }

    @RequestMapping("/register")
    public String register() {
        return "register2";
    }

    @RequestMapping("/hompageView")
    public String getHomePage() {
        return "hompage";
    }

    @ModelAttribute
    @PostMapping(value = "/homepage")
    public ModelAndView login(Login login, HttpSession session) {
        if (userService.checkLogin(login)) {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getUsername(),
                            login.getPassword()
                    )
            );
            session.setAttribute("userName", login.getUsername());
            return new ModelAndView("homepage");
        } else {
            return new ModelAndView("redirect:/homepage");
        }
    }

    @PostMapping("/registration")
    public String registration(User user) {
        if (userRespository.findByUserName(user.getUserName()) == null) {
            user.setRoles(new HashSet<>());
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.getRoles().add(roleRespository.findByNameContaining(Constants.ROLE_MEMBER));
            userRespository.save(user);
            return "login2";
        } else {
            return "userName is Existed";
        }

    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @RequestMapping("/createPostView")
    public String getCreatePostView() {
        return "createpost";
    }


    @RequestMapping("/getListPost")
    public String getListPost(Model model) {
        List<Post> posts = postResppository.findAll();
        for(Post post:posts){
            if(post.getContent().length()>250){
                post.setContent(post.getContent().substring(0,250)+"...");
            }
        }
        model.addAttribute("listpost", posts);
        return "listPost";
    }

    @ModelAttribute
    @PostMapping("/createPost")
    public String createPost(Post post, String listTag, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        User user = userRespository.findByUserName(userName);
        post.setTags(new ArrayList<>());
        String[] tags = listTag.split(",");
        for (String tag : tags) {
            if (tagRespository.findByNameContaining(tag) == null) {
                Tag tag1 = new Tag();
                tag1.setName(tag);
                tag1.setUser(user);
                tagRespository.save(tag1);
            }
            post.getTags().add(tagRespository.findByNameContaining(tag));
        }
        post.setUser(user);
        postResppository.save(post);
        return "createpost";
    }

    @RequestMapping("/postMimDi")
    public String getPostByID(@RequestParam int id, Model model,HttpSession session) {
        Post post = postResppository.findById(id);
        session.setAttribute("post",post);
        model.addAttribute("getPost", post);
        List<Comment> comments = commentRespository.findByPostID(id);
        model.addAttribute("listComment", comments);
        List<Tag> tags=tagRespository.findByPostID(id);
        model.addAttribute("tags",tags);
        return "post";
    }

    @RequestMapping("/comment")
    public String postComment(String content,HttpServletRequest request){
        Comment comment=new Comment();
        comment.setContent(content);
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        User user = userRespository.findByUserName(userName);
        comment.setUser(user);
        Post post=(Post)session.getAttribute("post");
        int id=post.getId();
        comment.setPost(post);
        commentRespository.save(comment);
        return "redirect:/postMimDi?id="+id ;
    }
    @RequestMapping("/myPost")
    public String getMyPost(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        User user = userRespository.findByUserName(userName);
        int id=user.getId();
        List<Post> posts = postResppository.findByUserID(id);
        for(Post post:posts){
            if(post.getContent().length()>250){
                post.setContent(post.getContent().substring(0,250)+"...");
            }
        }
        model.addAttribute("posts", posts);
        return "listMyPost";
    }

    @RequestMapping("/deletePost")
    public String deletePost(@RequestParam int id){
        Post post=postResppository.findById(id);
        postResppository.delete(post);
        return "redirect:/myPost";
    }

    @RequestMapping("/editView")
    public String editPostView(@RequestParam int id,Model model,HttpSession session){
        Post post=postResppository.findById(id);
        session.setAttribute("postEdit",post);
        List<Tag> tags=post.getTags();
        String tagStr="";
        for(Tag tag:tags){
            tagStr+=tag.getName()+",";
        }
        model.addAttribute("tagStr",tagStr);
        model.addAttribute("post",post);
        return "editView";
    }

    @RequestMapping("/editPost")
    public String editPost(Post post,HttpServletRequest request,String listTag){
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        User user = userRespository.findByUserName(userName);
        Post post1=(Post) session.getAttribute("postEdit");
        post1.setTitle(post.getTitle());
        post1.setContent(post.getContent());
        post1.setUser(user);
        post1.setTags(new ArrayList<>());
        String[] tags = listTag.split(",");
        for (String tag : tags) {
            if (tagRespository.findByNameContaining(tag) == null) {
                Tag tag1 = new Tag();
                tag1.setName(tag);
                tag1.setUser(user);
                tagRespository.save(tag1);
            }
            post1.getTags().add(tagRespository.findByNameContaining(tag));
        }
        postResppository.save(post1);
        int id=post1.getId();
        return "redirect:/postMimDi?id="+id;
    }

    @RequestMapping("/getPostByTag")
    public String getPostByTag(@RequestParam int id,Model model){
        List<Post> posts=postResppository.findByTagID(id);
        for(Post post:posts){
            if(post.getContent().length()>250){
                post.setContent(post.getContent().substring(0,250)+"...");
            }
        }
        model.addAttribute("postsByTag",posts);
        return "PostByTag";
    }
}
