package com.alvaro.demo;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alvaro.demo.Comment.Comment;
import com.alvaro.demo.Comment.CommentRepository;
import com.alvaro.demo.Entries.Entry;
import com.alvaro.demo.Entries.EntryRepository;
import com.alvaro.demo.Image.Image;
import com.alvaro.demo.User.User;
import com.alvaro.demo.User.UserRepository;
import com.alvaro.demo.Utils.ImageUtility;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {
    
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() throws IOException{
        File fi1 = new File("./src/main/resources/static/images/work_1.png");
        File fi2 = new File("./src/main/resources/static/images/work_2.png");
        File fi3 = new File("./src/main/resources/static/images/work_3.png");
        File fi4 = new File("./src/main/resources/static/images/work_4.png");
        byte[] fileContent1 = Files.readAllBytes(fi1.toPath());
        byte[] fileContent2 = Files.readAllBytes(fi2.toPath());
        byte[] fileContent3 = Files.readAllBytes(fi3.toPath());
        byte[] fileContent4 = Files.readAllBytes(fi4.toPath());
        Image img1 = new Image("work_1.jpg","image/png",ImageUtility.compressImage(fileContent1));
        Image img2 = new Image("work_2.jpg","image/png",ImageUtility.compressImage(fileContent2));
        Image img3 = new Image("work_3.jpg","image/png",ImageUtility.compressImage(fileContent3));
        Image img4 = new Image("work_4.jpg","image/png",ImageUtility.compressImage(fileContent4));

        System.out.println("Imagenes generadas");
        User admin = this.userRepository.findByName("admin");
        User user = this.userRepository.findByName("user");
        User user1 = this.userRepository.findByName("user1");
        User user2 = this.userRepository.findByName("user2");
        if(user==null){
            String[] roles = {"ROLE_USER"};
            userRepository.save(new User("user", "pass", roles));
        }
        if(user1==null){
            String[] roles = {"ROLE_USER"};
            userRepository.save(new User("user1", "pass", roles));
        }
        if(user2==null){
            String[] roles = {"ROLE_USER"};
            userRepository.save(new User("user2", "pass", roles));
        }
        if(admin==null) {
            String[] roles = {"ROLE_USER","ROLE_ADMIN"};
            userRepository.save(new User("admin", "pass",roles));
        }
        System.out.println("Usuarios generadas");
        
        List<Entry> entries = entryRepository.findAll();
        
        if(entries.size()==0){
            Entry entry1 = new Entry("Titulo 1","En este articulo hablaremos de como se implementan diferentes medidas de seguridad para evitar ataques informaticos.",user,img1);
            Entry entry2 = new Entry("Titulo 2","Actualmente la gente esta impresionada con la nueva IA conocida como CHATGPT la cual esta produciendo estragos.",user1,img2);
            Entry entry3 = new Entry("Titulo 3","Aparecen los primeros hackers en la guerra entre Ucrania y Rusia, provocando graves daños en sus infraestructuras.",user,img3);
            Entry entry4 = new Entry("Titulo 4","Ya podremos usar nuestros moviles en los aviones gracias al 5G, actualmente sera de pago pero de nuevo vemos como avanza la tecnologia.",user,img4);
            Comment comment1 = new Comment("Me ha parecido muy util e interesante",entry1,user1);
            Comment comment2 = new Comment("No sabia nada del tema y me he enterado bastante bien :)",entry1,user2);
            Comment comment3 = new Comment("Guau increible",entry2,user2);
            System.out.println("Entradas y comentarios listos");

            entryRepository.save(entry1);
            entryRepository.save(entry2);
            entryRepository.save(entry3);
            entryRepository.save(entry4);
            System.out.println("Post Guardados");
            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);
            System.out.println("Comments Guardados");
            
        }
       
        
    }

}
