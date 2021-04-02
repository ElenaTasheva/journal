package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.repositories.TopicRepository;
import com.example.my_bullet_journal.services.TopicService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TopicControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;



    @BeforeEach
    public void init()  {
        this.topicService.seedTopics();
    }


    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN", "USER"})
    public void showTopicsShowTopicsIfThereAreAnyInDB() throws Exception {
        this.mockMvc.perform(get("/topics/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("topics-all"))
                .andExpect(model()
                        .attributeExists("topics"));

    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN", "USER"})
    public void addTopicRedirectsAndDoesNOtAddTopicToTheRepoWhenThereAreMistakesInBinding () throws Exception {
        long count = topicRepository.count();
        this.mockMvc.perform(post("/topics/add")
                .param("title", "TEST")
                .param("img", "img.url")
                .with(csrf()))
                .andExpect(flash().attributeExists("topicBindingModel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        Assert.assertEquals(count, topicRepository.count());

    }
    }


