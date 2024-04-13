package com.chatApp.authentication.mapper;

import com.chatApp.authentication.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private User user;
}
