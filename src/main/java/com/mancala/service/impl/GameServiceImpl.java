package com.mancala.service.impl;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.mapper.GameMapper;
import com.mancala.model.dto.GameDto;
import com.mancala.model.entity.Game;
import com.mancala.repository.GameRepository;
import com.mancala.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameMapper mapper;
    private final GameRepository repository;

    @Override
    public GameDto initializeNewGame() {
        var game = repository.save(new Game());
        return mapper.entityToDto(game);
    }
}
