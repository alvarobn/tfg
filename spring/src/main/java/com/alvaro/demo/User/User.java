package com.alvaro.demo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alvaro.demo.Comment.Comment;
import com.alvaro.demo.Entries.Entry;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonIgnore
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "userEntry")
    private List<Entry> entries;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "userComment")
    private List<Comment> comments;

    private String email;

    public User(){}
    public User(String nombre, String password, String[] roles){
        this.setName(nombre);
        this.setPasswordHash(new BCryptPasswordEncoder().encode(password));
        this.setRoles(new ArrayList<>(Arrays.asList(roles)));
        this.setEntries(new LinkedList<>());
    }
    public User(String nombre, String password, String email, String[] roles){
        this.setName(nombre);
        this.setPasswordHash(new BCryptPasswordEncoder().encode(password));
        this.setRoles(new ArrayList<>(Arrays.asList(roles)));
        this.setEntries(new LinkedList<>());
        this.setEmail(email);
    }
    
    public long getId() { return this.id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getPasswordHash() { return this.passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash;}
    public List<String> getRoles() { return this.roles; 
    }
    public void setRoles(List<String> roles) { this.roles = roles; }
    public List<Entry> getEntries(){ return this.entries; }
    public void setEntries(List<Entry> entries){ this.entries = entries; }
    public List<Comment> getComments(){ return this.comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public String getEmail(){ return this.email; }
    public void setEmail(String email){ this.email = email; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        final User other = (User) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
