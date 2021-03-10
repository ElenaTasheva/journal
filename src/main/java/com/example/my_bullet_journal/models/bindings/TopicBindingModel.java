package com.example.my_bullet_journal.models.bindings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class TopicBindingModel {

    private String title;
    private String imageUrl;

    @NotBlank(message = "Please enter title")
    public String getTitle() {
        return title;
    }

    public TopicBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
    message = "Please enter a valid URL")
    public String getImageUrl() {
        return imageUrl;
    }

    public TopicBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public TopicBindingModel() {


    }
}
