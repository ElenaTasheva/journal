package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.bindings.CommentBindingModel;
import com.example.my_bullet_journal.models.entities.Comment;
import com.example.my_bullet_journal.repositories.CommentRepository;
import com.example.my_bullet_journal.services.CommentService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void addCommentAddsCommentToATopicAndRedirect() throws Exception {
        long count = commentRepository.count();
        this.mockMvc.perform(post("/topics/comments/{id}", 1).with(csrf()))
                .andExpect(status().is3xxRedirection());
        commentService.addCommentToTopic((long) 1, new CommentBindingModel().setText("test"),"admin@gmail.com");
        Assert.assertEquals(count + 1, commentRepository.count());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void deleteCommentDeletesCommentAndRedirects() throws Exception {
        long count = commentRepository.count();
        if(count == 0){
            commentService.addCommentToTopic((long) 1, new CommentBindingModel().setText("test"),"admin@gmail.com");
        }
        List<Comment> commentList = commentRepository.findAll();
        long id = commentList.get(0).getId();
        this.mockMvc.perform(delete("/topics//comments/{topicId}/delete/{commentId}", 1, id).with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assert.assertEquals(count - 1, commentRepository.count());
    }


}
