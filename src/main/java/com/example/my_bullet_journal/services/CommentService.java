package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.bindings.CommentBindingModel;
import com.example.my_bullet_journal.models.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    List<CommentViewModel> viewComments(Long topicId);


    void addCommentToTopic(Long id, CommentBindingModel commentBindingModel, String userEmail);

    void delete(long commentId);
}

