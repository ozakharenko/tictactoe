package com.app.client.model;


import java.io.Serializable;

public class CallbackResult implements Serializable {

    private String message;
    private Coordinates coordinates;

    public CallbackResult() {
    }

    public CallbackResult(String message, Coordinates coordinates) {
        this.message = message;
        this.coordinates = coordinates;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
