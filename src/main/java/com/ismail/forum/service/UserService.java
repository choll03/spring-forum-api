package com.ismail.forum.service;

import com.ismail.forum.entity.User;
import com.ismail.forum.error.NotFoundException;
import com.ismail.forum.helper.Response;
import com.ismail.forum.model.RegisterRequest;
import com.ismail.forum.model.UserResponse;
import com.ismail.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse register(RegisterRequest registerRequest) {
        this.userRepository.register(registerRequest.getName(), registerRequest.getEmail());
        return profile(this.userRepository.getLastId().intValue());
    }

    public UserResponse profile(Integer id) {
        User user = findUserOrNotFound(id);
        return Response.convertUserToResponse(user);
    }

    private User findUserOrNotFound(Integer id) {
        Optional<User> user = this.userRepository.findById(id);

        if(user.isEmpty()) {
            throw new NotFoundException("Data not Found");
        }
        return user.get();
    }

}
