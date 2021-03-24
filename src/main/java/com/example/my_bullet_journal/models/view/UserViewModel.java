package com.example.my_bullet_journal.models.view;

public class UserViewModel {

    private Long id;
    private String username;
    private String email;
    private String imgUrl = "/colorlib-regform-7/colorlib-regform-7/images/user_anonymous.png";


    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public UserViewModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }
}
