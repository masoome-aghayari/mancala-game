package com.mancala.service;

import com.mancala.MancalaTestsDataProvider;
import com.mancala.mapper.GameMapper;
import com.mancala.model.dto.GameDto;
import com.mancala.model.entity.Game;
import com.mancala.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/*
 * @author masoome.aghayari
 * @since 1/12/24
 */
@SpringBootTest
class GameServiceTest extends MancalaTestsDataProvider {

    @Autowired
    GameService gameService;

    @MockBean
    GameMapper mapper;

    @Test
    void givenGameObject_whenSaved_then_idIsNotNull() {
        var actualResult = gameService.initializeNewGame();
        assertNotNull(actualResult.getId());
    }

    @Test
    void createsGameObject_whenSaved_thenReturns_initializedGameDtoWithDefaults() {
        var gameDto = getGameDto();
        when(mapper.entityToDto(any(Game.class))).thenReturn(gameDto);
        var actualResult = gameService.initializeNewGame();
        assertEquals(gameDto.getBoard(),actualResult.getBoard());
        assertEquals(gameDto.getCurrentPlayer(),actualResult.getCurrentPlayer());
        assertEquals(gameDto.getWinner(),actualResult.getWinner());
        assertEquals(gameDto.getStatus(),actualResult.getStatus());
    }
}