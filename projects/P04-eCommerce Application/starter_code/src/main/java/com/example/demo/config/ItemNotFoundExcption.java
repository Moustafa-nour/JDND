package com.example.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,value = HttpStatus.NOT_FOUND, reason = "Some thing went wrong, Element cannot be found!")
public class ItemNotFoundExcption extends RuntimeException {

    public ItemNotFoundExcption(String message) {
        super(message);
    }

    public ItemNotFoundExcption() {
    }
}
