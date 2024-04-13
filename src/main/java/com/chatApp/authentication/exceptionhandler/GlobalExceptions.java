package com.chatApp.authentication.exceptionhandler;

import com.chatApp.authentication.exceptions.UserNotFound;
import com.chatApp.authentication.service.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map> nullPointerExeception(NullPointerException nullPointerException){
        return CustomResponse.ok("error due to null value please check");
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Map> nullPointerExeception(UserNotFound userNotFound){
        return CustomResponse.ok("user not available for tthis id");
    }
}
