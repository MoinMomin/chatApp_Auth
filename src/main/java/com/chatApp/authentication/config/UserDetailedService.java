package com.chatApp.authentication.config;

import com.chatApp.authentication.model.User;
import com.chatApp.authentication.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailedService implements UserDetailsService {
    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userDAO.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("user not found");
        }
        return new UserDetailMapper(user);
    }
}
