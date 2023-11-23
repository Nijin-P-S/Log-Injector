package com.nijin.logs.entity;

import lombok.Data;

@Data
public class ErrorResponse extends Response{
    private String message;
    private Object data;
}
