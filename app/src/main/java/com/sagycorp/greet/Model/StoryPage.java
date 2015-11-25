package com.sagycorp.greet.Model;

/**
 * Created by Dzeko on 11/9/2015.
 */
public class StoryPage {
    private String Title,Description,ImageURL;

    public StoryPage() {
    }

    /*public StoryPage(String title, String description, String imageURL) {
        Title = title;
     *//*   Description = description;
        ImageURL = imageURL;*//*
    }*/

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
