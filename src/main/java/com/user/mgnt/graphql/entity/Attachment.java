package com.user.mgnt.graphql.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "attachment")
@Entity
public class Attachment extends BaseEntity {
    @Lob
    private String data;
    private String name;
    @Column(name = "content_type")
    private String contentType;
    private String size;
    @Column(name = "date_uploaded")
    private Timestamp dateUploaded;
    private String attachmentId;
    private boolean isTempered;
}
