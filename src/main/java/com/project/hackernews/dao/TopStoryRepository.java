package com.project.hackernews.dao;

import com.project.hackernews.model.TopStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopStoryRepository extends JpaRepository<TopStory, Integer> {
}
