package com.mancala.model.dto;

/*
 * @author masoome.aghayari
 * @since 12/29/23
 */

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int errorCode;
    private String path;
    private Date date;

    public ErrorResponse(String message, int errorCode, Date date, String path) {
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
        this.date = date;
    }
}
