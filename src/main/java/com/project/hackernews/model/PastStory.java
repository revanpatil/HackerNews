package com.project.hackernews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "paststories")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PastStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int primaryId;
    @Column(name = "title")
    private String title;
    @Column(name = "url")
    private String url;
    @Column(name = "time")
    private long time;
    @Column(name = "story_id")
    private int id;

    public PastStory() {

    }

    public PastStory(int id, String title, String url, long time) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.time = time;
    }

    @Override
    public String toString() {
        return "PastStory{" +
                "primaryId=" + primaryId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", time=" + time +
                ", id=" + id +
                '}';
    }
}
