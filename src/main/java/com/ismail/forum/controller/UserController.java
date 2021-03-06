package com.ismail.forum.controller;

import com.ismail.forum.model.RegisterRequest;
import com.ismail.forum.model.UserResponse;
import com.ismail.forum.model.WebResponse;
import com.ismail.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public WebResponse<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new WebResponse<UserResponse>(
                201,
                "ok",
                this.userService.register(registerRequest)
        );
    }

    @GetMapping("/profile/{id}")
    public WebResponse<UserResponse> profile(@PathVariable Integer id) {
        return new WebResponse<UserResponse>(
                200,
                "ok",
                this.userService.profile(id)
        );
    }
}
