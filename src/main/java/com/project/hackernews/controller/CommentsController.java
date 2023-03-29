package com.project.hackernews.controller;

import com.project.hackernews.model.Comment;
import com.project.hackernews.service.HackerNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class CommentsController {

    @Autowired
    private HackerNewsService hackerNewsService;

    @GetMapping("/comments/{storyId}")
    public ResponseEntity<List<Comment>> showComments(@PathVariable("storyId") String storyId) throws IOException {
        return hackerNewsService.getComments(storyId);

    }
}
