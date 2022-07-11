package com.example.webdownloadapp.exceptions;

public class RequiredFieldException extends Exception {
    private static final long serialVersionUID = 1L;

    public RequiredFieldException(String message) {
        super(message);
    }
    public RequiredFieldException(String model,String field){
        super(field+" field on "+model+" is required");
    }
}
