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
   // private final CloudinaryService cloudinaryService;


    public TopicServiceImpl(ModelMapper modelMapper, TopicRepository topicRepository) {
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;
       // this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<TopicViewModel> getAllTopics() {
        //TODO throw an error if none or return something
        return this.topicRepository.findAll()
                .stream().map(topic -> {
                    return  this.modelMapper.map(topic, TopicViewModel.class);
                }).collect(Collectors.toList());
    }



    @Override
    public Topic findByName(String name) {
        return this.topicRepository.findByTitle(name).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public TopicServiceModel getTopicById(Long id) {
        Topic topic = this.topicRepository.findById(id).orElse(null);
        //todo change method if it returns null
        return this.modelMapper.map(topic, TopicServiceModel.class);
    }

    @Override
    public Topic findByid(Long id) {
        return this.topicRepository.findById(id).orElse(null);
        //todo deal with the optional
    }

    @Override
    public void seedTopics() {
        // todo change it to 0; save the pictures in the project
        if(topicRepository.count() <= 1) {
            Topic topic = new Topic("What are the recent accomplishments that make me feel proud and successful?",
                    "https://stunningmotivation.com/wp-content/uploads/2018/07/motivational-question-1.jpg");
            Topic topic1 = new Topic("What is the good advice that the 50 years old me will tell the 20 years old me?",
                    "https://stunningmotivation.com/wp-content/uploads/2018/07/motivational-question-8.jpg");
            topicRepository.save(topic);
            topicRepository.save(topic1);

        }
    }

    @Override
    public void save(TopicBindingModel topicBindingmodel) throws IOException {
        if(this.topicRepository.findByTitle(topicBindingmodel.getTitle()).isPresent()){
            throw new IllegalArgumentException("Topic already exist");
            //todo Make a custom Exception
        }
        else{
//            MultipartFile img = topicBindingmodel.getImg();
//            String imageUrl = cloudinaryService.uploadImage(img);
            Topic topic = this.modelMapper.map(topicBindingmodel, Topic.class);
            this.topicRepository.save(topic);
        }
    }
}
