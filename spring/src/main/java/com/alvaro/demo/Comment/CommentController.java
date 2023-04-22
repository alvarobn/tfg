package com.alvaro.demo.Comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.alvaro.demo.Entries.Entry;
import com.alvaro.demo.Entries.EntryService;
import com.alvaro.demo.User.User;
import com.alvaro.demo.User.UserComponent;
import com.alvaro.demo.User.UserService;



@Controller
public class CommentController {
 
    @Autowired
    CommentService commentService;
    @Autowired
    UserComponent userComponent;
    @Autowired
    UserService userService;
    @Autowired
    EntryService entryService;



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

    @GetMapping("/comments/insert")
    public String formInsertComment(Model model, @RequestParam String comentary, @RequestParam Long entryId){
        User user = userComponent.getLoggedUser();
        Entry entry = entryService.getEntryById(entryId);
        if(user!=null && entry!=null){
            Comment newComment = new Comment(comentary,entry,user);
            if(commentService.insertComment(newComment)) return "successfully";
            else return "error";
        }else return "error";
    }
    @GetMapping("/comments/delete")
    public String deleteComment(Model model, @RequestParam Long commentId){
        User user = userComponent.getLoggedUser();
        if(userComponent.isAdmin()){
            if(commentService.deleteComment(commentId)!=null) return "successfully";
            else return "error";
        }else{
            if(user.equals(commentService.getCommentById(commentId).getUser())){
                if(commentService.deleteComment(commentId)!=null) return "successfully";
                else return "error";
            }
        }
        return "error";
    }

}
