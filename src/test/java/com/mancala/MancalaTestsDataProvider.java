package com.mancala;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.model.dto.GameDto;
import com.mancala.model.dto.PlayRequestModel;
import com.mancala.model.entity.Game;
import com.mancala.model.enums.GameStatus;

import java.util.UUID;

public class MancalaTestsDataProvider {

    public GameDto getGameDto() {
        var game = getNewGame();
        return GameDto.builder()
                .id(game.getId())
                .status(GameStatus.IN_PROGRESS)
                .board(game.getBoard())
                .currentPlayer(game.getCurrentPlayer())
                .winner(game.getWinner())
                .build();
    }

    public Game getNewGame() {
        return new Game();
    }

    public PlayRequestModel getPlayRequestModel(UUID gameId) {
        var requestModel = new PlayRequestModel();
        requestModel.setGameId(gameId);
        requestModel.setPocketIndex(0);
        return requestModel;
    }
}
