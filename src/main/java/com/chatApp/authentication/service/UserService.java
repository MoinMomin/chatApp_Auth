package com.chatApp.authentication.service;


import com.chatApp.authentication.model.User;

public interface UserService {
    public User signUp(User user);
    public User updateUser(User user);
    public User getUserById(long userId);
}
