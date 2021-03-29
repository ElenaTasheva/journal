package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.repositories.CommentRepository;
import org.junit.Before;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

public class CommentServiceTest {

    private CommentRepository mockedCommentRepository;
    private UserService mockedUserService;
    private ModelMapper modelMapper;
    private TopicService mockedTopicService;



    @Before
    public void init(){
        this.mockedUserService = Mockito.mock(UserService.class);
        this.modelMapper = new ModelMapper();
        this.mockedCommentRepository = Mockito.mock(CommentRepository.class);
        this.mockedTopicService = Mockito.mock(TopicService.class);

    }
}
