package com.sparta.post03.controller;

import com.sparta.post03.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;



@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService heartService;


    //좋아요
    @PostMapping("/api/auth/post/{id}/like")
    public ResponseEntity<?> pushPostLike(@PathVariable Long id, HttpServletRequest request){
        return heartService.pushPostLike(id, request);
    }
}

