package com.mancala.controller;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.api.GameApi;
import com.mancala.model.dto.GameDto;
import com.mancala.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(GameApi.BASE_PATH)
public class GameController implements GameApi {
    private  final GameService gameService;

    @Override
    @GetMapping(START)
    public ResponseEntity<GameDto> startNewGame() {
        return ResponseEntity.ok(gameService.initializeNewGame());
    }
}
