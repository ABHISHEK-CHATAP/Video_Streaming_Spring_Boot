package com.streaming.app.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.streaming.app.entities.Video;
import com.streaming.app.repositories.VideoRepository;
import com.streaming.app.services.VideoService;

import jakarta.annotation.PostConstruct;

@Service
public class VideoServiceImpl implements  VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Value("${files.video}")
    String DIR;

    @PostConstruct
    public void init(){

        File file = new File(DIR);
        if(!file.exists()){
            file.mkdir();
            System.out.println("Folder created");
        }else{
            System.out.println("Folder already created.");
        }
    }

    @Override
    public Video save(Video video, MultipartFile file) {

       try {
         //original file name
         String filename = file.getOriginalFilename();
         String contentType = file.getContentType();
         InputStream inputStream = file.getInputStream();
 
 
        //file path : create 
        String cleanFileName = StringUtils.cleanPath(filename);
        //folder path : create 
        String cleanFolder = StringUtils.cleanPath(DIR);
        //folder path with filename
        Path path = Paths.get(cleanFolder, cleanFileName);
        System.out.println("path of folder and file -"+ path);
 
        //copy file to the folder
        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
 
 
        // video metadata 
        video.setContentType(contentType);
        video.setFilePath(path.toString());

        //metadata save
        return videoRepository.save(video);
 
 
       } catch (IOException e) {
            e.printStackTrace();
            return null;
       }

    }

    @Override
    public Video get(String videoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Video getByTitle(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByTitle'");
    }

    @Override
    public List<Video> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

}
