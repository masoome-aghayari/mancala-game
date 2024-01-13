package com.mancala.model.exceptions;

/*
 * @author masoome.aghayari
 * @since 1/13/24
 */

public class GameIsOverException extends RuntimeException {
    public GameIsOverException(String message) {
        super(message);
    }
}
