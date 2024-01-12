package com.mancala.controller;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.api.GameApi;
import com.mancala.model.dto.MancalaGameDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(GameApi.BASE_PATH)
public class GameController implements GameApi {
    @Override
    public ResponseEntity<MancalaGameDto> startNewGame() {
        return null;
    }
}
