package com.example.learn_english_smart.Model;

public class SerpApiSearchException extends Exception {
    public SerpApiSearchException() {
        super();
    }

    /**
     * Constructor
     * @param exception original exception
     */
    public SerpApiSearchException(Exception exception) {
        super(exception);
    }

    /**
     * Constructor
     * @param message short description of the root cause
     */
    public SerpApiSearchException(String message) {
        super(message);
    }
}
