package com.utn.corralon.exception;

public class StockInsufficientException extends RuntimeException{
    public StockInsufficientException(String message) {
        super(message);
    }
}
