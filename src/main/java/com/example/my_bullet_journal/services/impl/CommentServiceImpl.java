package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.bindings.CommentBindingModel;
import com.example.my_bullet_journal.models.entities.Comment;
import com.example.my_bullet_journal.models.entities.Topic;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.view.CommentViewModel;
import com.example.my_bullet_journal.repositories.CommentRepository;
import com.example.my_bullet_journal.services.CommentService;
import com.example.my_bullet_journal.services.TopicService;
import com.example.my_bullet_journal.services.UserService;
import org.apache.http.HttpException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final TopicService topicService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, TopicService topicService, UserService userService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.topicService = topicService;
        this.userService = userService;
    }

    @Override
    public List<CommentViewModel> viewComments(Long topicid) {
        return this.commentRepository.findAllByTopic_Id(topicid)
                .stream().map(comment -> {
                    CommentViewModel view = this.modelMapper.map(comment, CommentViewModel.class);
                    view.setUsername(comment.getUser().getUsername());
                    view.setEmail(comment.getUser().getEmail());
                    return view;
                })

                .collect(Collectors.toList());
    }

    @Override
    public Long getCommentIdByName(String name) {
        return this.commentRepository.getIdByName(name);
    }

    @Override
    public void addCommentToToppic(Long id, CommentBindingModel commentBindingModel, String userEmail) {
        User user = this.userService.findByEmail(userEmail);
        Topic topic = this.topicService.findByid(id);
        Comment comment = this.modelMapper.map(commentBindingModel, Comment.class);
        comment.setTopic(topic);
        comment.setUser(user);
        this.commentRepository.save(comment);

    }

    @Override
    public void delete(long commentId) throws HttpException {
       Comment comment = this.commentRepository.findById(commentId).orElseThrow(HttpException::new);
       this.commentRepository.delete(comment);
    }
}
