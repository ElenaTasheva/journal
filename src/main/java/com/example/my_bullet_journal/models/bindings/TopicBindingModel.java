package com.example.my_bullet_journal.models.bindings;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TopicBindingModel {

    private String title;
    private MultipartFile img;

    @NotBlank(message = "Please enter title")
    public String getTitle() {
        return title;
    }

    public TopicBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }


    public MultipartFile getImg() {
        return img;
    }

    public TopicBindingModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    public TopicBindingModel() {


    }
}
