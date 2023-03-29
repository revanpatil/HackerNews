package com.project.hackernews.dao;

import com.project.hackernews.model.PastStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastStoryRepository extends JpaRepository<PastStory, Integer> {
    boolean existsById(Long id);
}
