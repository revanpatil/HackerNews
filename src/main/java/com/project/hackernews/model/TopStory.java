package com.project.hackernews.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "topstories")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int primaryId;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "score")
    private int score;

    @Column(name = "time")
    private long time;

    @Column(name = "user")
    private String by;

    @Column(name = "story_id")
    private int id;

}
