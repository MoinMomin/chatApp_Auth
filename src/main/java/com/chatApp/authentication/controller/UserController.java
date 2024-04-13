package com.chatApp.authentication.controller;

import com.chatApp.authentication.config.UserDetailedService;
import com.chatApp.authentication.mapper.LoginRequest;
import com.chatApp.authentication.mapper.LoginResponse;
import com.chatApp.authentication.model.User;
import com.chatApp.authentication.repository.UserDAO;
import com.chatApp.authentication.service.CustomResponse;
import com.chatApp.authentication.service.UserService;
import com.chatApp.authentication.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailedService userDetailedService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    UserDAO userDAO;

    @CrossOrigin
    @PostMapping("/registeruser")
    public ResponseEntity<Map> signUp(@RequestBody User user) {
        if (user != null && (user.getName() == null || user.getName() == "" || user.getUserName() == null || user.getUserName() == "" || user.getPassword() == null || user.getPassword() == "")) {
            return CustomResponse.badRequest("request is not proper");
        }
        userService.signUp(user);
        return CustomResponse.ok(user);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<Map> getUser(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return CustomResponse.ok(user);
        }
        return CustomResponse.conflict("user Not Found");
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody LoginRequest loginRequest) {
        try {
            log.info(String.valueOf(loginRequest));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        } catch (UsernameNotFoundException e) {
            // throw new RuntimeException();
            return CustomResponse.conflict("------------------------");
        } catch (Exception e) {
            return CustomResponse.conflict(e.getMessage());
        }
        UserDetails userDetails = userDetailedService.loadUserByUsername(loginRequest.getUserName());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUser(userDAO.findByUserName(loginRequest.getUserName()));
        return CustomResponse.ok(loginResponse);
    }
    @GetMapping("/authentincate")
    public ResponseEntity<Map> authenticateToken(HttpServletRequest request) {
       /* String requestTokenHeader=request.getHeader("Authorization");
        String username=null;
        String jwttoken;
        if (requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer")){
            jwttoken=requestTokenHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwttoken);
            }catch(Exception e){
                return CustomResponse.forbidden("invalid or expired Token");
            }
            UserDetails userDetails=userDetailedService.loadUserByUsername(username);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.getContext().setAuthentication(null);
            }else  {
                // Handle invalid or expired token
                return CustomResponse.forbidden("invalid or expired Token");
            }
            //filterChain.doFilter(request,response);
        }else{
            // Handle null  token
            return CustomResponse.forbidden("Token not Available");
        }*/
        return CustomResponse.ok("user Verified");
    }

}