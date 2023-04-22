package com.alvaro.demo.Comment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Comment> getAll(){
        return commentRepository.findAll();
    }
    public Comment getCommentById(long id){
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) return comment.get();
        else return null;
    }
    public boolean insertComment(Comment newComment) {
        if(!commentRepository.existsById(newComment.getId())){
            commentRepository.save(newComment);
            return true;
        }else return false;
    }
    public Comment deleteComment(long id){
        if(commentRepository.existsById(id)){
            Comment toReturn = commentRepository.findById(id).get();
            toReturn.getEntry();
            commentRepository.deleteById(id);
            return toReturn;
        }else return null;
    }


     //DINAMIC QUERYS
     public List<Comment> findCommentsByAutor(String autor){
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.autor = :autor",Comment.class);
        return query.setParameter("autor",autor).getResultList();
    }
}
