package com.alvaro.demo.Entries;
import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alvaro.demo.Image.Image;
import com.alvaro.demo.Image.ImageService;
import com.alvaro.demo.User.User;
import com.alvaro.demo.User.UserComponent;
import com.alvaro.demo.Utils.ImageUtility;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class EntryController {

    @Autowired
    EntryService entryService;
    @Autowired
    UserComponent userComponent;

    @Autowired
    ImageService imageService;

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


    @GetMapping("/blog")
    public String blog(Model model){
        List<Entry> allEntries = entryService.getAll();
        model.addAttribute("listEntries",allEntries);
        return "blog";
    }
    @PostMapping("/blog/insert")
    public String formInsertEntry(Model modelo,@RequestParam String titulo, @RequestParam String contenido, @RequestParam MultipartFile file) throws IOException{
        Image image = Image.builder().name(file.getOriginalFilename())
        .type(file.getContentType()).image(ImageUtility.compressImage(file.getBytes()))
        .build();
        User user = userComponent.getLoggedUser();
        System.out.println(file.getBytes());
        Entry newEntry = new Entry(titulo,contenido,user,image);
        if(entryService.insertEntry(newEntry)) return "successfully";
        else return "error";
    }
    @GetMapping("/blog/{id}")
    public String post(Model model, @PathVariable long id){
        Entry entry = entryService.getEntryById(id);
        if(entry != null){
            entry.setContenido(lineBreaks(entry.getContenido()));
            model.addAttribute("entry", entry);
            return "post";
        }else{
            model.addAttribute("property", HttpStatus.NOT_FOUND);
            return "error";
        }
    }

    @GetMapping("/blog/{id}/image")
    public void showEntryImage(@PathVariable Long id,HttpServletResponse response) throws IOException {
        Entry entry = entryService.getEntryById(id);
        Image image = entry.getImage();
        response.setContentType(image.getType());
        InputStream is = new ByteArrayInputStream(ImageUtility.decompressImage(image.getImage()));
        IOUtils.copy(is, response.getOutputStream());
    }


    @GetMapping("/blog/post")
    public String newPost(Model model){
        return "newPost";
    }

    @GetMapping("/entries/delete")
    public String deleteEntry(Model model, @RequestParam Long entryId){
        User user = userComponent.getLoggedUser();
        if(userComponent.isAdmin()){
            if(entryService.deleteEntry(entryId)!=null) return "successfully";
            else return "error";
        }else{
            if(entryService.getEntryById(entryId).getUser().equals(user)){
                if(entryService.deleteEntry(entryId)!=null) return "successfully";
                else return "error";
            }
        }
        return "error";
    }

    public String lineBreaks(String text) {
        // Reemplazar los saltos de línea por <br>
        String processedText = text.replace("\n", "<br>");
        // Devolver el texto procesado
        return processedText;
    }
    
}
