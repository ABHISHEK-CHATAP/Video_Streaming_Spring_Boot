package com.streaming.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.streaming.app.entities.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video,String>{

    // ye VideoRepository hume CRUD operation methods toh de dega ,
    // still hume custom methods chahiye toh likh sakte hai

    // like, hume Title se find krna hai video toh

    Optional<Video> findByTitle(String title);

}
