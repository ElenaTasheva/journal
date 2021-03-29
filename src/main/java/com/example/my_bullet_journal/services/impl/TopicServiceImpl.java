package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.bindings.TopicBindingModel;
import com.example.my_bullet_journal.models.entities.Topic;
import com.example.my_bullet_journal.models.services.TopicServiceModel;
import com.example.my_bullet_journal.models.view.TopicViewModel;
import com.example.my_bullet_journal.repositories.TopicRepository;
import com.example.my_bullet_journal.services.CloudinaryService;
import com.example.my_bullet_journal.services.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {


    private final ModelMapper modelMapper;
    private final TopicRepository topicRepository;
    private final CloudinaryService cloudinaryService;
    private final String PATH_1 = "/colorlib-regform-7/colorlib-regform-7/images/motivational-question-1.jpg";
    private final String PATH_2 = "/colorlib-regform-7/colorlib-regform-7/images/motivational-question-8.jpg";
    private final String PATH_3 = "/colorlib-regform-7/colorlib-regform-7/images/motivational-question-2.jpg";


    public TopicServiceImpl(ModelMapper modelMapper, TopicRepository topicRepository, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;

        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<TopicViewModel> getAllTopics() {
        List<TopicViewModel> topics = this.topicRepository.findAll()
                .stream().map(topic -> {
                    return  this.modelMapper.map(topic, TopicViewModel.class);
                }).collect(Collectors.toList());
       if(topics.size() > 0){
           return  topics;
       }
       throw  new IllegalArgumentException("There are no topics to be shown");
    }



    @Override
    public Topic findByName(String name) {
        return this.topicRepository.findByTitle(name).orElseThrow(() -> new IllegalArgumentException("The topic you are looking for does not exist"));
    }

    @Override
    public TopicServiceModel getTopicById(Long id) {
        Topic topic = this.topicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no topic with such id"));
        return this.modelMapper.map(topic, TopicServiceModel.class);
    }

    @Override
    public Topic findByid(Long id) {
        return this.topicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no topic with such id"));
    }

    @Override
    public void seedTopics() {
        if(topicRepository.count() == 0) {
            Topic topic = new Topic("What are the recent accomplishments that make me feel proud and successful?",
                    PATH_1);
            Topic topic1 = new Topic("What is the good advice that the 50 years old me will tell the 20 years old me?",
                    PATH_2);
            Topic topic2 =  new Topic("What is the one step I can take right now to move closer to my goal?",
                    PATH_3);
            topicRepository.save(topic);
            topicRepository.save(topic1);
            topicRepository.save(topic2);

        }
    }

    @Override
    public void save(TopicBindingModel topicBindingmodel) throws IOException {
        if(this.topicRepository.findByTitle(topicBindingmodel.getTitle()).isPresent()){
            throw new IllegalArgumentException("Topic already exist");
        }
        else{
            MultipartFile img = topicBindingmodel.getImg();
            String imageUrl = cloudinaryService.uploadImage(img);
            Topic topic = this.modelMapper.map(topicBindingmodel, Topic.class);
            topic.setImageUrl(imageUrl);
            this.topicRepository.save(topic);
        }
    }
}
