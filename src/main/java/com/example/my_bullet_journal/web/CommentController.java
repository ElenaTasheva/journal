package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.CommentBindingModel;
import com.example.my_bullet_journal.services.CommentService;
import com.example.my_bullet_journal.services.TopicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topics")
public class CommentController {

    private final CommentService commentService;
    private final TopicService topicService;

    public CommentController(CommentService commentService, TopicService topicService) {
        this.commentService = commentService;
        this.topicService = topicService;
    }

    @GetMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showComments (@PathVariable Long id, Model model){
        model.addAttribute("comments", this.commentService.viewComments(id));
        model.addAttribute("topic", this.topicService.getTopicById(id));
        return "show-comments";

    }

    @PostMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addComment(@PathVariable Long id, CommentBindingModel commentBindingModel) {
        this.commentService.addCommentToToppic(id, commentBindingModel);
        return "redirect:" +  id;
    }
}
