package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.bindings.CommentBindingModel;
import com.example.my_bullet_journal.models.bindings.TopicBindingModel;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;
import com.example.my_bullet_journal.services.CommentService;
import com.example.my_bullet_journal.services.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public TopicController(TopicService topicService, CommentService commentService, ModelMapper modelMapper) {
        this.topicService = topicService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/all")
    public String showAll(Model model){

        model.addAttribute("topics", topicService.getAllTopics());
        return "topics-all";
    }

    @PostMapping("/add")
    public String addTopic(@Valid TopicBindingModel topicBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicBindingModel", topicBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.topicBindingModel", bindingResult);
            return "redirect:/admin";
        }

        topicService.save(topicBindingModel);

        return "redirect:all";

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
