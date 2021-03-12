package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.bindings.TopicBindingModel;
import com.example.my_bullet_journal.models.entities.Topic;
import com.example.my_bullet_journal.models.services.TopicServiceModel;
import com.example.my_bullet_journal.models.view.TopicViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TopicService {

    List<TopicViewModel> getAllTopics();



    Topic findByName(String name);

    TopicServiceModel getTopicById(Long id);

    Topic findByid(Long id);


    void seedTopics();

    void save(TopicBindingModel topicBindingmodel) throws IOException;
}
