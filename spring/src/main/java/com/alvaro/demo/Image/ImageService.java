package com.alvaro.demo.Image;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image getImageById(long id){
        Optional<Image> image = imageRepository.findById(id);
        if(image.isPresent()) return image.get();
        else return null;
    }
    
}
