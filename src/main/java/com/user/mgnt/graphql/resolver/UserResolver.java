package com.user.mgnt.graphql.resolver;

import com.user.mgnt.graphql.dto.Response;
import com.user.mgnt.graphql.dto.SignInRequest;
import com.user.mgnt.graphql.entity.Attachment;
import com.user.mgnt.graphql.entity.User;
import com.user.mgnt.graphql.service.AttachmentService;
import com.user.mgnt.graphql.service.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@GraphQLApi
@Component
public class UserResolver {
    private final UserService userService;
    private final AttachmentService attachmentService;

    @GraphQLMutation(name = "SignIn")
    public Response signInUser(
            @GraphQLArgument(name = "signIn") @Validated SignInRequest signInRequest
    ) {
        return userService.signInUser(signInRequest);
    }

    @GraphQLMutation(name = "SignUp")
    public Response signUpUser(
            @GraphQLArgument(name = "SignUp") @Validated User user
    ) {
        return userService.signUpUser(user);
    }

    @GraphQLMutation(name = "UploadAttachment")
    public Response uploadAttachment(
            @GraphQLArgument(name = "UploadAttachment") @Validated Attachment attachment
    ) {
        return attachmentService.uploadAttachment(attachment);
    }

    @GraphQLQuery(name = "Attachments")
    public Response fetchAttachments() {
        return attachmentService.fetchAttachments();
    }

    @GraphQLQuery(name = "AttachmentById")
    public Response attachmentById(
            @GraphQLArgument(name = "AttachmentByID") @Validated Long id
    ) {
        return attachmentService.attachmentById(id);
    }

    @GraphQLMutation(name = "DeleteAttachment")
    public Response deleteAttachment(
            @GraphQLArgument(name = "DeleteAttachment") @Validated Long id
    ) {
        return attachmentService.deleteAttachment(id);
    }
}
