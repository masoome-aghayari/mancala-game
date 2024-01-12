package com.mancala.controller;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameControllerTests {

    @Mock
    GameController gameController;

    @Test
    void startNewGameTest(){
        var actualResult = gameController.startNewGame();
        Assertions.assertNull(actualResult);
    }

}
