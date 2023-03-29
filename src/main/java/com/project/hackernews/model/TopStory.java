package com.project.hackernews.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopStory {

    private String title;

    private String url;

    private int score;

    private long time;

    private String by;

    private int id;

}
