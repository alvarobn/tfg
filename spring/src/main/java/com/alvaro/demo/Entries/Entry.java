package com.alvaro.demo.Entries;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.alvaro.demo.Comment.Comment;
import com.alvaro.demo.Image.Image;
import com.alvaro.demo.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.SimpleDateFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="entries")
public class Entry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    private Date fecha;

    private String titulo;
    private String autor;
    private String contenido;
    @JsonIgnore
    private String descripcion;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "entry")
    private List<Comment> comments;

    @JsonIgnore
    @ManyToOne
    private User userEntry;

    @JsonIgnore
    @OneToOne(cascade=CascadeType.ALL)
	private Image image;

    public Entry(){}
    public Entry(Long id,String titulo, String contenido, User user, Image image){
        setId(id);
        setFecha();
        setTitulo(titulo);
        setAutor(user.getName());
        setContenido(contenido);
        setDescripcion(contenido);
        setComments(new LinkedList<>());
        setUser(user);
        setImage(image);
    }
    public Entry(String titulo, String contenido, User user, Image image){
        setFecha();
        setTitulo(titulo);
        setAutor(user.getName());
        setContenido(contenido);
        setDescripcion(contenido);
        setComments(new LinkedList<>());
        setUser(user);
        setImage(image);
    }
    public Entry(Long id,String titulo, String contenido, User user){
        setId(id);
        setFecha();
        setTitulo(titulo);
        setAutor(user.getName());
        setContenido(contenido);
        setDescripcion(contenido);
        setComments(new LinkedList<>());
        setUser(user);
    }
    public Entry(String titulo, String contenido, User user){
        setFecha();
        setTitulo(titulo);
        setAutor(user.getName());
        setContenido(contenido);
        setDescripcion(contenido);
        setComments(new LinkedList<>());
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
    public void setTitulo(String titulo){ this.titulo = titulo; }
    public String getTitulo(){ return this.titulo; }
    public void setAutor(String autor){ this.autor = autor; }
    public String getAutor(){ return this.autor; }
    public void setContenido(String contenido){ this.contenido = contenido; }
    public String getContenido(){ return this.contenido; }
    public void setDescripcion(String contenido){
        String[] cadenas = contenido.split(" ");
        String descripcion = "";
        int i = 0;
        while(i<=10 && i < cadenas.length){
            descripcion += cadenas[i] + " ";
            i++;
        }
        descripcion += " ...";
        this.descripcion = descripcion;
    }
    public String getDescripcion(){ return this.descripcion; }
    public void setComments(List<Comment> comments){ this.comments = comments; }
    public List<Comment> getComments(){ return this.comments;};
    public void setUser(User user){ this.userEntry = user; }
    public User getUser(){ return this.userEntry; }
    public void setImage(Image image){ this.image=image; }
    public Image getImage(){ return this.image; }
}
