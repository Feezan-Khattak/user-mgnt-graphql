package com.user.mgnt.graphql.repository;

import com.user.mgnt.graphql.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByAttachmentId(String attachmentId);
    void removeByAttachmentId(String attachmentId);
}
