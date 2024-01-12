package com.mancala.mapper;

import com.mancala.MancalaTestsDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

@SpringBootTest(classes = {GameMapperImpl.class})
class GameMapperTests extends MancalaTestsDataProvider {

    @Autowired
    GameMapper gameMapper;

    @Test
    void entityToDtoConverterTest() {
        var actualResult = gameMapper.entityToDto(getNewGame());
        var expectedResult = getGameDto();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void dtoToEntityConverterTest() {
        var actualResult = gameMapper.dtoToEntity(getGameDto());
        var expectedResult = getNewGame();
        assertEquals(expectedResult, actualResult);
    }
}