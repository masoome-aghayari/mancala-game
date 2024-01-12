package com.mancala.controller;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.MancalaTestsDataProvider;
import com.mancala.mapper.GameMapper;
import com.mancala.model.dto.GameDto;
import com.mancala.model.entity.Game;
import com.mancala.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTests extends MancalaTestsDataProvider {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    GameService gameService;

    @MockBean
    GameMapper mapper;


    @Test
    void startNewGameTest() {
        var expectedResult = getGameDto();
        expectedResult.setId(UUID.randomUUID());
        when(gameService.initializeNewGame()).thenReturn(expectedResult);
        when(mapper.entityToDto(any(Game.class))).thenReturn(expectedResult);
        var actualResult = this.restTemplate.getForObject("http://localhost:" + port + "/api/mancala/start",
                GameDto.class);
        assertEquals(expectedResult, actualResult);
    }
}
