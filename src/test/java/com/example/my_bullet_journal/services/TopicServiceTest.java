package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.bindings.TopicBindingModel;
import com.example.my_bullet_journal.models.entities.Topic;
import com.example.my_bullet_journal.models.view.TopicViewModel;
import com.example.my_bullet_journal.repositories.TopicRepository;
import com.example.my_bullet_journal.services.impl.TopicServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TopicServiceTest {

    private TopicRepository mockedTopicRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private CloudinaryService cloudinaryService;


    @Before
    public void init () {
        this.mockedTopicRepository = Mockito.mock(TopicRepository.class);
        this.cloudinaryService = Mockito.mock(CloudinaryService.class);
    }

    @Test
    public void getAllTopicsReturnsListOfTopicViewModelIfThereAreAnyInTHeDB() {
        Mockito.when(this.mockedTopicRepository.findAll()).thenReturn(List.of(new Topic(), new Topic()));
        TopicService topicService = new TopicServiceImpl(modelMapper, mockedTopicRepository, cloudinaryService);
        List<TopicViewModel> models = topicService.getAllTopics();
        for (TopicViewModel model : models) {
            Assert.assertEquals(TopicViewModel.class, model.getClass());

        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllTopicsThrowsExceptionIfThereAreNoTopicsInTheDB() {
        Mockito.when(this.mockedTopicRepository.findAll()).thenReturn(new ArrayList<>());
        TopicService topicService = new TopicServiceImpl(modelMapper, mockedTopicRepository, cloudinaryService);
        topicService.getAllTopics();

    }

    @Test
    public void findByTitlereturnsTopicByName(){
        Topic testTopic = new Topic();
        testTopic.setTitle("testTopic");
        Mockito.when(this.mockedTopicRepository.findByTitle("testTopic")).thenReturn(java.util.Optional.of(testTopic));
        TopicService topicService = new TopicServiceImpl(modelMapper, mockedTopicRepository, cloudinaryService);
        Assert.assertEquals(testTopic,topicService.findByName(testTopic.getTitle()));
    }


    @Test (expected = IllegalArgumentException.class)
    public void findByTitleThrowsExceptionWhenTopicDoesNotExist(){
        Topic testTopic = new Topic();
        testTopic.setTitle("testTopic");
        Mockito.when(this.mockedTopicRepository.findByTitle("testTopic")).thenReturn(Optional.empty());
        TopicService topicService = new TopicServiceImpl(modelMapper, mockedTopicRepository, cloudinaryService);
        topicService.findByName(testTopic.getTitle());
    }

    @Test
    public void findByIdReturnsTopicById(){
        Topic testTopic = new Topic("Test", "dhffj.bg");
        testTopic.setId((long) 1);
        Mockito.when(this.mockedTopicRepository.findById((long) 1)).thenReturn(Optional.of(testTopic));
        TopicService topicService = new TopicServiceImpl(modelMapper, mockedTopicRepository, cloudinaryService);
        Assert.assertEquals(testTopic,topicService.findByid((long)1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void findByIdThrowsExceptionWhenTopicDoesNotExist(){
        Mockito.when(this.mockedTopicRepository.findById((long) 1)).thenReturn(Optional.empty());
        TopicService topicService = new TopicServiceImpl(modelMapper, mockedTopicRepository, cloudinaryService);
        topicService.findByid((long)1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void saveTopicThrowsExceptionWhenTopicAlreadyExist() throws IOException {
        Topic testTopic = new Topic();
        testTopic.setTitle("testTopic");
        TopicBindingModel topicBindingModel = new TopicBindingModel();
        topicBindingModel.setTitle("testTopic");
        Mockito.when(this.mockedTopicRepository.findByTitle("testTopic")).thenReturn(Optional.of(testTopic));
        TopicService topicService = new TopicServiceImpl(modelMapper, mockedTopicRepository, cloudinaryService);
        topicService.save(topicBindingModel);
    }

    @Test
    public void saveTopicDoesNotThrowExceptionWhenTopicDoesNotExist() throws IOException {
        TopicBindingModel topicBindingModel = new TopicBindingModel();
        topicBindingModel.setTitle("testTopic");
        Mockito.when(this.mockedTopicRepository.findByTitle("testTopic")).thenReturn(Optional.empty());
        TopicService topicService = new TopicServiceImpl(modelMapper, mockedTopicRepository, cloudinaryService);
        topicService.save(topicBindingModel);
    }




}


