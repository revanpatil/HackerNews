package com.project.hackernews.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hackernews.dao.PastStoryRepository;
import com.project.hackernews.model.Comment;
import com.project.hackernews.model.PastStory;
import com.project.hackernews.model.TopStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
@CacheConfig(cacheNames = {"topStories"})
public class HackerNewsService {

    private static final String API_BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    private static final int TOP_STORIES_LIMIT = 10;

    private static final String COMMENT_API_URL = "https://hacker-news.firebaseio.com/v0/item/%d.json";
    private final RestTemplate restTemplate;
    Set<Integer> addedStoryIds = new HashSet<>();
//    @Autowired
//    private TopStoryRepository topStoryRepository;
    @Autowired
    private PastStoryRepository pastStoryRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public HackerNewsService() {
        restTemplate = new RestTemplate();
    }

    public List<TopStory> fetchTopStories() throws IOException {

        // Get the IDs of the top stories
        String topStoriesUrl = API_BASE_URL + "topstories.json";
        String[] topStoryIds = new ObjectMapper().readValue(new URL(topStoriesUrl), String[].class);

        // Get the details of the top stories
        List<TopStory> topStories = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<TopStory>> futures = new ArrayList<>();

        for (String topStoryId : topStoryIds) {
            String storyUrl = API_BASE_URL + "item/" + topStoryId + ".json";
            Callable<TopStory> task = () -> new ObjectMapper().readValue(new URL(storyUrl), TopStory.class);
            Future<TopStory> future = executorService.submit(task);
            futures.add(future);
        }

        executorService.shutdown();
        for (Future<TopStory> future : futures) {
            try {
                TopStory story = future.get();
                topStories.add(story);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Sort the top stories by score, null values last
       Collections.sort(topStories, Comparator.nullsLast((d1, d2) -> Integer.compare(d2.getScore(), d1.getScore())));

        List<TopStory> updatedList = new ArrayList<>();
        System.out.println("Api Hit on " + LocalDateTime.now());

        for (int i = 0; i < TOP_STORIES_LIMIT; i++) {
            updatedList.add(topStories.get(i));
        }

        savePastStories(updatedList);
        return updatedList;

    }

    //To save 10 Top Story in PastStory
    public void savePastStories(List<TopStory> topStories) {
        List<PastStory> pastStories = new ArrayList<>();
        if (!CollectionUtils.isEmpty(topStories)) {
            for (TopStory story : topStories) {
                if (!addedStoryIds.contains(story.getId())) {
                    PastStory p = new PastStory(story.getId(), story.getTitle(), story.getUrl(), story.getTime());
                    pastStoryRepository.save(p);
                    addedStoryIds.add(story.getId());
                }
            }
        }
    }


    public List<PastStory> getPastStories() {
        return pastStoryRepository.findAll();
    }

    public Comment getStory(String storyId) throws IOException {
        //String url = String.format(ITEM_URL, storyId);
        String url = API_BASE_URL + "item/" + storyId + ".json";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String body = response.getBody();
        return objectMapper.readValue(body, Comment.class);
    }

    public Comment getComment(Integer commentId) throws IOException {
        String url = String.format(COMMENT_API_URL, commentId);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String body = response.getBody();
        return objectMapper.readValue(body, Comment.class);
    }

    public ResponseEntity<List<Comment>> getComments(String storyId) throws IOException {
        // Retrieve the story details from the Hacker News API
        Comment story = getStory(storyId);

        if (story == null) {
            Comment c = new Comment();
            c.setText("Invalid Story Id");
            List<Comment> error = new ArrayList<>();
            error.add(c);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }


        // Extract the IDs of the comments associated with the story
        List<Integer> kids = story.getKids();

        // Retrieve the details of each comment and store in a list
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Comment>> futures = new ArrayList<>();

        for (Integer commentId : kids) {
            Callable<Comment> task = () -> getComment(commentId);
            Future<Comment> future = executorService.submit(task);
            futures.add(future);
        }

        executorService.shutdown();

        List<Comment> comments = new ArrayList<>();
        for (Future<Comment> future : futures) {
            try {
                Comment comment = future.get();
                comments.add(comment);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Sort the comments by the total number of child comments each comment has, in descending order
        Collections.sort(comments, (d1, d2) -> {
            int size1 = d1.getKids() != null ? d1.getKids().size() : 0;
            int size2 = d2.getKids() != null ? d2.getKids().size() : 0;
            return Integer.compare(size2, size1);
        });

        // Return a maximum of 10 comments in the response (or fewer, if there are fewer than 10 comments for the given story)
        int numComments = Math.min(comments.size(), 10);
        return ResponseEntity.ok(comments.subList(0, numComments));
    }
}