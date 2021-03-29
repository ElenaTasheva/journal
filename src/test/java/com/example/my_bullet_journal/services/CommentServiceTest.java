package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.Comment;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.view.CommentViewModel;
import com.example.my_bullet_journal.repositories.CommentRepository;
import com.example.my_bullet_journal.services.impl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void viewCommentsReturnsAListOfCommentsViewModel(){
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment().setText("Testing comment").setUser(new User()));
        Mockito.when(mockedCommentRepository.findAllByTopic_Id((long) 1))
                .thenReturn(comments);
        CommentService commentService = new CommentServiceImpl(mockedCommentRepository,modelMapper,mockedTopicService,mockedUserService);
        List<CommentViewModel> views = commentService.viewComments((long) 1);
        Assert.assertEquals("Testing comment", views.get(0).getText());
        Assert.assertEquals(CommentViewModel.class, views.get(0).getClass());

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCommentThrowsExceptionIfCommentDoesNotExist(){
        Mockito.when(mockedCommentRepository.findById((long) 1)).thenReturn(Optional.empty());
        CommentService commentService = new CommentServiceImpl(mockedCommentRepository,modelMapper,mockedTopicService,mockedUserService);
        commentService.delete((long) 1);
    }

    @Test
    public void deleteCommentDoesNotThrowExceptionIfCommentExists(){
        Mockito.when(mockedCommentRepository.findById((long) 1)).thenReturn(Optional.of(new Comment()));
        CommentService commentService = new CommentServiceImpl(mockedCommentRepository,modelMapper,mockedTopicService,mockedUserService);
        commentService.delete((long) 1);
    }


}
