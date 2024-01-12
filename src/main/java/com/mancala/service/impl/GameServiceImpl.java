package com.mancala.service.impl;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.mapper.GameMapper;
import com.mancala.model.dto.GameDto;
import com.mancala.model.dto.PlayRequestModel;
import com.mancala.model.entity.Game;
import com.mancala.model.enums.GameStatus;
import com.mancala.repository.GameRepository;
import com.mancala.service.GamePlayer;
import com.mancala.service.GameService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameMapper mapper;
    private final GameRepository repository;
    private final GamePlayer gamePlayer;

    @Override
    public GameDto initializeNewGame() {
        var game = repository.save(new Game());
        return mapper.entityToDto(game);
    }

    @Override
    public GameDto play(PlayRequestModel requestModel) {
        var game = getGame(requestModel.getGameId());
        var gameDto = mapper.entityToDto(game);
        gamePlayer.setGame(gameDto);
        gamePlayer.play(requestModel.getPocketIndex());
        checkGameOver();
        gameDto = gamePlayer.getGame();
        game = mapper.dtoToEntity(gameDto);
        repository.save(game);
        return gameDto;
    }

    private void checkGameOver() {
        if (gamePlayer.isGameOver()) {
            gamePlayer.findWinner();
            gamePlayer.getGame().setStatus(GameStatus.OVER);
        }
    }

    private Game getGame(UUID gameId) {
        return repository.findById(gameId).orElseThrow(EntityNotFoundException::new);
    }
}
