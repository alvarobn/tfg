package com.alvaro.demo.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;


    public List<User> getAll(){ return this.userRepository.findAll();}
    public User getByName(String name){return userRepository.findByName(name);}
    public User getById(long id){return userRepository.findById(id);}
    public User deleteUser(long id){
        if(userRepository.existsById(id)){
            User toReturn = userRepository.findById(id);
            toReturn.getComments().size();
            toReturn.getEntries().size();
            userRepository.deleteById(id);
            return toReturn;
        }else return null;
    }
    public User saveUser(User newUser){
        return userRepository.save(newUser);
    }
}
