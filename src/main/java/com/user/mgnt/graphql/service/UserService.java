package com.user.mgnt.graphql.service;

import com.user.mgnt.graphql.dto.Response;
import com.user.mgnt.graphql.dto.SignInRequest;
import com.user.mgnt.graphql.entity.Attachment;
import com.user.mgnt.graphql.entity.User;
import com.user.mgnt.graphql.repository.AttachmentRepo;
import com.user.mgnt.graphql.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public Response signInUser(SignInRequest signInRequest) {
        Response response = new Response();
        Optional<User> user = userRepo.findByEmailAndPassword(signInRequest.getEmail(), signInRequest.getPassword());
        if (user.isPresent()) {
            response.setStatus("SUCCESS");
            response.setMessage("Login successfully");
            response.setObject(user.get());
        } else {
            response.setStatus("FAIL");
            response.setMessage("Fail to login");
            response.setObject(null);
        }
        return response;
    }

    @Transactional
    public Response signUpUser(User user) {
        Response response = new Response();
        User saved;
        Optional<User> fetchedUser = userRepo.findByEmail(user.getEmail());
        if (fetchedUser.isPresent()) {
            user.setId(fetchedUser.get().getId());
            user.setEmail(fetchedUser.get().getEmail());
            user.setUserId(fetchedUser.get().getUserId());
        } else {
            user.setUserId(UUID.randomUUID().toString());
        }
        log.info("Saving User: {}, {}", user.getEmail(), user.getUserId());
        try {
            saved = userRepo.save(user);
            response.setStatus("SUCCESS");
            response.setMessage("Login successfully");
            response.setObject(saved);
        } catch (Exception er) {
            response.setStatus("FAIL");
            response.setMessage("Fail to save the user");
            response.setObject(er.getMessage());
        }
        return response;
    }
}
