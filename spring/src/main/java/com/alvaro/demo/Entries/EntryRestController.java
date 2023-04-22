package com.alvaro.demo.Entries;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EntryRestController {
    
    @Autowired
    EntryService entryService;

    @GetMapping("/entries")
    public ResponseEntity<Collection<Entry>> entries(Model model, @RequestParam Optional<String> autor, @RequestParam Optional<String> titulo){
        List<Entry> listEntries;
        if(autor.isPresent() && titulo.isPresent()) { listEntries = entryService.findEntriesByAutorAndTitulo(autor.get(), titulo.get()); }
        else if(autor.isPresent()) { listEntries = entryService.findEntriesByAutor(autor.get()); } 
        else if(titulo.isPresent()) { listEntries = entryService.findEntriesByTitulo(titulo.get()); }
        else listEntries = entryService.getAll();
        if(listEntries.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(listEntries,HttpStatus.OK);
    }
    @GetMapping("/entries/{id}")
    public ResponseEntity<Entry> entriesId(Model model, @PathVariable int id){
        Entry entry = entryService.getEntryById(id);
        if(entry!=null) return new ResponseEntity<>(entry,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/entries")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Entry> insertEntry(Model model, @RequestBody Entry entry){
        if(entryService.insertEntry(entry)) return new ResponseEntity<>(entry,HttpStatus.OK);
        else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Entry> deleteEntry(Model model, @RequestParam int id){
        Entry entryDelete = entryService.deleteEntry(id);
        if(entryDelete != null) return new ResponseEntity<>(entryDelete,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/entries/{id}")
    public ResponseEntity<Entry> updateEntry(Model model, @PathVariable int id,@RequestBody Entry newEntry){
        Entry entryToupdate = entryService.updateEntry(id, newEntry);
        if(entryToupdate != null){
            return new ResponseEntity<>(entryToupdate,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
