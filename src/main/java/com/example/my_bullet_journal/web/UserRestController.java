package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.view.UserViewModel;
import com.example.my_bullet_journal.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserViewModel>> findAll() {
        return ResponseEntity
                .ok()
                .body(userService.findAll());
    }



}

