package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.CommentBindingModel;
import com.example.my_bullet_journal.services.CommentService;
import com.example.my_bullet_journal.services.TopicService;
import com.example.my_bullet_journal.web.annotations.PageTitle;
import org.apache.http.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    @PageTitle("Comments")
    public String showComments (@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("comments", this.commentService.viewComments(id));
        model.addAttribute("topic", this.topicService.getTopicById(id));
        model.addAttribute("namePrincipal", userDetails.getUsername());
        model.addAttribute("hasRoleAdmin", userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        return "show-comments";

    }

    @PostMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addComment(@PathVariable Long id, @Valid CommentBindingModel commentBindingModel,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal UserDetails user) {
        if(bindingResult.hasErrors()){
            return "redirect:" +  id;
        }
        this.commentService.addCommentToTopic(id, commentBindingModel,user.getUsername());
        return "redirect:" +  id;
    }


    @DeleteMapping("/comments/{topicId}/delete/{commentId}")
    public String delete(@PathVariable long topicId, @PathVariable long commentId) {
        commentService.delete(commentId);
        return "redirect:/topics/comments/" +  topicId;
    }
}
