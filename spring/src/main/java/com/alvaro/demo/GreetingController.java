package com.alvaro.demo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alvaro.demo.Comment.Comment;
import com.alvaro.demo.Comment.CommentService;
import com.alvaro.demo.Entries.Entry;
import com.alvaro.demo.Entries.EntryService;
import com.alvaro.demo.User.User;
import com.alvaro.demo.User.UserComponent;
import com.alvaro.demo.User.UserService;

@Controller
public class GreetingController {
    
    @Autowired
    private UserComponent userComponent;
    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @ModelAttribute
    public void addUserToModel(Model model) {
        boolean registered = userComponent.isRegistered();
        boolean logged = userComponent.getLoggedUser() != null;
        model.addAttribute("registered", registered);
        model.addAttribute("logged", logged);
        if(logged) {
            model.addAttribute("admin", userComponent.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
            model.addAttribute("userName",userComponent.getLoggedUser().getName());
        }
    }


    @GetMapping("/")
    public String home(Model model){
        return "home";
    }
    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model){
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(Model modelo, @RequestParam String name, @RequestParam String password){
        User newUser = userService.getByName(name);
        if(newUser != null){
            return "error";
        }else{
            String[] roles = {"ROLE_USER"};
            newUser = new User(name,password,"",roles);
            userService.saveUser(newUser);
            userComponent.setLoggedUser(newUser);
            return "successfully";
        }
    }
    @GetMapping("/panel")
    public String panel(Model model){
        User user = userComponent.getLoggedUser();
        if(user!=null) {
            if(userComponent.isAdmin()){
                List<Entry> entries = entryService.getAll();
                List<Comment> comments = commentService.getAll();
                model.addAttribute("listEntries", entries);
                model.addAttribute("listComments", comments);
            }else{
                List<Entry> entries = entryService.findEntriesByAutor(user.getName());
                List<Comment> comments = commentService.findCommentsByAutor(user.getName());
                model.addAttribute("listEntries", entries);
                model.addAttribute("listComments", comments);
            }
            return "panel";         
        }
        return "error";
    }

    @GetMapping("/docs")
    public String docs(Model model){
        return "openapi";
    }
}
