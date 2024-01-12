package com.mancala.model.exceptions;

/*
 * @author masoome.aghayari
 * @since 1/11/24
 */

public class EmptyPocketException extends IllegalArgumentException {
    public EmptyPocketException(String message) {
        super(message);
    }
}
