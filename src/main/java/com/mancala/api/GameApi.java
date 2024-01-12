package com.mancala.api;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.model.dto.GameDto;
import org.springframework.http.ResponseEntity;

public interface GameApi {
    String BASE_PATH = "/mancala";
    String START = "/start";
    String PLAY = "/play";

    ResponseEntity<GameDto> startNewGame();
}
