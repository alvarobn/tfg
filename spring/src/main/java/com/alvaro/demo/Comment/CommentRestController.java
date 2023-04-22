package com.alvaro.demo.Comment;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentRestController {
    
    @Autowired
    CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<Collection<Comment>> comments(Model model){
        List<Comment> listComments = commentService.getAll();
        if(listComments.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(listComments,HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> commentsId(Model model, @PathVariable int id){
        Comment comment = commentService.getCommentById(id);
        if(comment == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(comment,HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comment> deleteComment(Model model, @RequestParam int id){
        Comment commentDelete = commentService.deleteComment(id);
        if(commentDelete != null) return new ResponseEntity<>(commentDelete,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
