package com.mancala.service;

import com.mancala.MancalaTestsDataProvider;
import com.mancala.model.enums.Player;
import com.mancala.model.exceptions.EmptyPocketException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


/*
 * @author masoome.aghayari
 * @since 1/13/24
 */
@SpringBootTest
class GamePlayerTest extends MancalaTestsDataProvider {

    private final UUID gameId = UUID.randomUUID();

    @Autowired
    GamePlayer gamePlayer;

    @Test
    void givenPocketIndex_when_PlayerDoesNotOwnIt_thenThrows_IllegalArgumentException() {
        var gameDto = getGameDto();
        gameDto.setId(gameId);
        gamePlayer.setGame(gameDto);
        var actualResult = assertThrows(IllegalArgumentException.class, () -> gamePlayer.play(7));
        String actualMessage = actualResult.getMessage();
        String expectedMessage = "choose from your own pockets";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenPocketIndex_when_PocketIsEmpty_thenThrows_EmptyPocketException() {
        var gameDto = getGameDto();
        gameDto.setBoard(new int[]{0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0});
        gameDto.setId(gameId);
        gamePlayer.setGame(gameDto);
        var actualResult = assertThrows(EmptyPocketException.class, () -> gamePlayer.play(0));
        String actualMessage = actualResult.getMessage();
        String expectedMessage = "you can't choose an empty pocket";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenPocketIndex_when_PocketIsNotEmpty_and_playerOwnsIt_then_plays() {
        var gameDto = getGameDto();
        gameDto.setBoard(new int[]{1, 12, 10, 9, 8, 7, 1, 6, 6, 6, 0, 1, 2, 3});
        int[] expectedAfterPlayBoard = new int[]{2, 0, 11, 10, 9, 8, 2, 7, 7, 7, 1, 2, 3, 3};
        gameDto.setId(gameId);
        gamePlayer.setGame(gameDto);
        gamePlayer.play(1);
        var actualAfterPlayBoard = gamePlayer.getGame().getBoard();
        assertArrayEquals(expectedAfterPlayBoard, actualAfterPlayBoard);
    }

    @Test
    void findWinner() {
        var gameDto = getGameDto();
        gameDto.setBoard(new int[]{0, 0, 0, 2, 1, 0, 37, 0, 0, 0, 0, 0, 0, 20});
        gameDto.setCurrentPlayer(Player.PLAYER_A);
        gameDto.setId(gameId);
        gamePlayer.setGame(gameDto);
        gamePlayer.findWinner();
        var actualResult = gamePlayer.getGame().getWinner();
        var expectedResult = Player.PLAYER_A.name();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void given_board_when_isGameOver_returns_true() {
        var board = new int[]{0, 0, 0, 2, 1, 0, 37, 0, 0, 0, 0, 0, 0, 20};
        var gameDto = getGameDto();
        gameDto.setBoard(board);
        gameDto.setCurrentPlayer(Player.PLAYER_A);
        gameDto.setId(gameId);
        gamePlayer.setGame(gameDto);
        var gameOver = gamePlayer.isGameOver();
        assertTrue(gameOver);
    }

    @Test
    void given_board_when_isNotGameOver_returns_false() {
        var board = new int[]{0, 0, 0, 2, 1, 0, 37, 0, 0, 0, 0, 0, 1, 19};
        var gameDto = getGameDto();
        gameDto.setBoard(board);
        gameDto.setCurrentPlayer(Player.PLAYER_A);
        gameDto.setId(gameId);
        gamePlayer.setGame(gameDto);
        var gameOver = gamePlayer.isGameOver();
        assertFalse(gameOver);
    }
}