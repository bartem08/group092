package com.interview.model;


import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Greeting implements Serializable {

    @Id
    private String id;

    private String message;

    public Greeting() { }

    public Greeting(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

}
