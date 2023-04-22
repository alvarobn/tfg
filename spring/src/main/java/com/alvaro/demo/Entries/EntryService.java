package com.alvaro.demo.Entries;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Entry> getAll(){
        return entryRepository.findAll();
    }
    public Entry getEntryById(long id){
        Optional<Entry> entry = entryRepository.findById(id);
        if(entry.isPresent()) return entry.get();
        else return null;
    }
    public boolean insertEntry(Entry newEntry) {
        if(!entryRepository.existsById(newEntry.getId())){
            entryRepository.save(newEntry);
            return true;
        }else return false;
    }
    public Entry deleteEntry(long id){
        if(entryRepository.existsById(id)){
            Entry toReturn = entryRepository.findById(id).get();
            toReturn.getComments().size();
            entryRepository.deleteById(id);
            return toReturn;
        }else return null;
    }
    public Entry updateEntry(long id, Entry entry){
        if(entryRepository.existsById(id)){
            Entry newEntry = new Entry(entry.getId(),entry.getTitulo(),entry.getContenido(),entry.getUser(),entry.getImage());
            newEntry.setId(id);
            entryRepository.deleteById(id);
            entryRepository.saveAndFlush(newEntry);
            return newEntry;
        }
        return null;
    }

    
    //SINGLE QUERYS
    public Entry findByTitulo(String titulo){
        TypedQuery<Entry> query = entityManager.createQuery("SELECT c FROM Entry c WHERE c.titulo = :titulo",Entry.class);
        return query.setParameter("titulo",titulo).getSingleResult();
    }

    //DINAMIC QUERYS
    public List<Entry> findEntriesByAutor(String autor){
        TypedQuery<Entry> query = entityManager.createQuery("SELECT c FROM Entry c WHERE c.autor = :autor",Entry.class);
        return query.setParameter("autor",autor).getResultList();
    }
    public List<Entry> findEntriesByTitulo(String titulo){
        TypedQuery<Entry> query = entityManager.createQuery("SELECT c FROM Entry c WHERE c.titulo = :titulo",Entry.class);
        return query.setParameter("titulo",titulo).getResultList();
    }
    public List<Entry> findEntriesByAutorAndTitulo(String autor, String titulo){
        TypedQuery<Entry> query = entityManager.createQuery("SELECT c FROM Entry c WHERE c.autor = :autor and c.titulo = :titulo",Entry.class);
        return query.setParameter("autor",autor).setParameter("titulo",titulo).getResultList();
    }

}
