package com.mancala.api;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.model.dto.GameDto;
import com.mancala.model.dto.PlayRequestModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Mancala Game", description = "Mancala Game Management APIs")
public interface GameApi {
    String BASE_PATH = "/mancala";
    String START = "/start";
    String PLAY = "/play";

    @Operation(summary = "Start a new Game",
            description = "creates a new MancalaGame instance with default values. " + "then stores that instance into database",
            tags = {"Mancala Game"})
    ResponseEntity<GameDto> startNewGame();

    @Operation(summary = "play game", description = "This api gets", tags = {"Mancala Game"})
    ResponseEntity<GameDto> play(PlayRequestModel requestModel);
}
