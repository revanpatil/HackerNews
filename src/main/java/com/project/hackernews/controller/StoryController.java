package com.project.hackernews.controller;

import com.project.hackernews.model.PastStory;
import com.project.hackernews.model.TopStory;
import com.project.hackernews.service.HackerNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class StoryController {

    @Autowired
    private HackerNewsService hackerNewsService;

    @GetMapping("/top-stories")
    @Cacheable("stories")
    public List<TopStory> getTopStories() throws IOException {
        return hackerNewsService.fetchTopStories();
    }


    @GetMapping("/past-stories")
    public List<PastStory> getPastStories() {
        return hackerNewsService.getPastStories();
    }
}
