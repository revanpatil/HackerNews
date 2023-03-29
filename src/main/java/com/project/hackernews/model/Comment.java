package com.project.hackernews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Comment {
    private String text;
    private String by;
    private int descendants;
    private List<Integer> kids;

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", by='" + by + '\'' +
                ", descendants=" + descendants +
                ", kids=" + kids +
                '}';
    }
}
