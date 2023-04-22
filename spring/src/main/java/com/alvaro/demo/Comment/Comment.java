package com.alvaro.demo.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.alvaro.demo.Entries.Entry;
import com.alvaro.demo.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    private Date fecha;

    private String autor;
    private String contenido;

    @JsonIgnore
    @ManyToOne
    private Entry entry;


    @JsonIgnore
    @ManyToOne
    private User userComment;

    public Comment(){}
    public Comment(String contenido, Entry entry, User user){
        setFecha();
        setAutor(user.getName());
        setContenido(contenido);
        setEntry(entry);
        setUser(user);
    }


    public void setId(long id){ this.id = id; }
    public long getId(){ return this.id; }
    public void setFecha(){this.fecha = new Date();}
    public String getFecha() {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate= DateFor.format(this.fecha);
        return stringDate;
    }
    public void setAutor(String autor){ this.autor = autor; }
    public String getAutor(){ return this.autor; }
    public void setContenido(String contenido){ this.contenido = contenido; }
    public String getContenido(){ return this.contenido; }
    public void setEntry(Entry entry){ this.entry = entry; }
    public Entry getEntry(){ return this.entry; }
    public void setUser(User user){ this.userComment = user; }
    public User getUser(){ return this.userComment; }

}
