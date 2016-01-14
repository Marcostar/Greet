package com.sagycorp.greet.Model;

/**
 * Created by Dzeko on 1/4/2016.
 */
public class StoryArchiveModel {
    private String index;
    private String title;
    private String url;


    public StoryArchiveModel() {
    }

    public StoryArchiveModel(String index, String title, String url) {
        this.index = index;
        this.title = title;
        this.url = url;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
