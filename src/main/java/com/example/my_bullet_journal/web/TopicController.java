package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.bindings.CommentBindingModel;
import com.example.my_bullet_journal.models.services.TopicServiceModel;
import com.example.my_bullet_journal.services.CommentService;
import com.example.my_bullet_journal.services.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final CommentService commentService;

    public TopicController(TopicService topicService, CommentService commentService) {
        this.topicService = topicService;
        this.commentService = commentService;
    }


    @GetMapping("/all")
    public String showAll(Model model){

        model.addAttribute("topics", topicService.getAllTopics());
        return "topics-all";
    }


    @GetMapping("/comments/{id}")
    public String showComments (@PathVariable Long id, Model model){
        model.addAttribute("comments", this.commentService.viewComments(id));
        model.addAttribute("topic", this.topicService.getTopicById(id));
        return "show-comments";

    }

    @PostMapping("/comments/{id}")
    public String addComment(@PathVariable Long id, CommentBindingModel commentBindingModel) {
        this.commentService.addCommentToToppic(id, commentBindingModel);
        return "redirect:" +  id;
    }


}
