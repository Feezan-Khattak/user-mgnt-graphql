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
    private final AttachmentRepo attachmentRepo;

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

    public Response uploadAttachment(Attachment attachment) {
        Response response = new Response();
        Optional<Attachment> fetchedAttachment = Optional.empty();
        Attachment savedAttachment;
        if (attachment.getAttachmentId() != null) {
            fetchedAttachment = attachmentRepo.findByAttachmentId(attachment.getAttachmentId());
        } else {
            attachment.setDateUploaded(Timestamp.valueOf(LocalDateTime.now()));
            attachment.setAttachmentId(UUID.randomUUID().toString());
        }
        if (fetchedAttachment.isPresent()) {
            attachment.setId(fetchedAttachment.get().getId());
            attachment.setAttachmentId(fetchedAttachment.get().getAttachmentId());
            attachment.setDateUploaded(Timestamp.valueOf(LocalDateTime.now()));
            attachment.setTempered(true);
        }
        try {
            savedAttachment = attachmentRepo.save(attachment);
            response.setStatus("SUCCESS");
            response.setMessage("Attachment Uploaded successfully");
            response.setObject(savedAttachment);
        } catch (Exception er) {
            response.setStatus("FAIL");
            response.setMessage("Fail to upload the attachment");
            response.setObject(er.getMessage());
        }
        return response;
    }

    public Response updateAttachment(Attachment attachment) {
        return null;
    }

    public Response fetchAttachments() {
        Response response = new Response();
        try {
            List<Attachment> attachments = attachmentRepo.findAll();
            response.setStatus("SUCCESS");
            response.setMessage("Attachment fetched successfully");
            response.setObject(attachments);
        } catch (Exception er) {
            response.setStatus("FAIL");
            response.setMessage("Failed to fetched the attachments");
            response.setObject(er.getMessage());
        }

        return response;
    }

    public Response attachmentById(String attachmentId) {
        Response response = new Response();
        Optional<Attachment> attachment = attachmentRepo.findByAttachmentId(attachmentId);
        if (attachment.isPresent()) {
            response.setStatus("SUCCESS");
            response.setMessage("Attachment fetched successfully");
            response.setObject(attachment.get());
        } else {
            response.setStatus("FAIL");
            response.setMessage("Failed to find the attachment using id: " + attachmentId);
            response.setObject(null);
        }
        return response;
    }

    public Response deleteAttachment(Long id) {
        Response response = new Response();
        try {
            attachmentRepo.deleteById(id);
            response.setStatus("SUCCESS");
            response.setMessage("Attachment deleted successfully");
            response.setObject(null);
        } catch (Exception er) {
            response.setStatus("FAIL");
            response.setMessage("Failed to delete the attachment");
            response.setObject(er.getMessage());
        }
        return response;
    }
}
