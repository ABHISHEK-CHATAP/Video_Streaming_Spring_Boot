package com.streaming.app.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.streaming.app.entities.Video;
import com.streaming.app.payload.CustomMessage;
import com.streaming.app.services.VideoService;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private VideoService videoService;

    //default constructor banya hai Autowiring Automatically ho jayegi ...
    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<?> create(
        @RequestParam("file")MultipartFile file,
        @RequestParam("title") String title,
        @RequestParam("description") String description
    ){

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoId(UUID.randomUUID().toString());

        Video saveVideo = videoService.save(video, file);

        if (saveVideo != null){
            return ResponseEntity.status(HttpStatus.OK).body(video);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(CustomMessage.builder().message("Video not uploaded").success(false).build());
        }

    }

}
