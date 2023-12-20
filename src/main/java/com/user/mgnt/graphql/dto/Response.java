package com.user.mgnt.graphql.dto;

import lombok.Data;

@Data
public class Response {
    private String status;
    private String message;
    private Object object;
}
