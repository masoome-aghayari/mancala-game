package com.mancala.service;

import com.mancala.MancalaTestsDataProvider;
import com.mancala.mapper.GameMapper;
import com.mancala.model.entity.Game;
import com.mancala.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @MockBean
    GameRepository repository;

    @Test
    void givenGameObject_whenSaved_then_idIsNotNull() {
        var expectedResult = getGameDto();
        var game = getNewGame();
        var gameId = UUID.randomUUID();
        game.setId(gameId);
        expectedResult.setId(gameId);
        when(repository.save(any(Game.class))).thenReturn(game);
        when(mapper.entityToDto(any(Game.class))).thenReturn(expectedResult);
        var actualResult = gameService.initializeNewGame();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void givenPlayRequestModel_when_PlayerDoesNotOwnPocket_thenThrows_IllegalArgumentException() {
        var gameDto = getGameDto();
        var game = getNewGame();
        gameDto.setId(UUID.randomUUID());
        game.setId(UUID.randomUUID());
        when(mapper.entityToDto(any(Game.class))).thenReturn(gameDto);
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(game));
        var requestModel = getPlayRequestModel(gameDto.getId());
        requestModel.setPocketIndex(8);
        var actualResult = assertThrows(IllegalArgumentException.class, () -> gameService.play(requestModel));
        String actualMessage = actualResult.getMessage();
        String expectedMessage = "choose from your own pockets";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenPlayRequestModel_when_NoGameWithSpecifiedIdNotFound_thenThrows_EntityNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        var result = assertThrows(EntityNotFoundException.class,
                () -> gameService.play(getPlayRequestModel(UUID.randomUUID())));
        var expectedExceptionName = "EntityNotFoundException";
        var actualExceptionName = result.getClass().getSimpleName();
        assertEquals(expectedExceptionName, actualExceptionName);
    }
}