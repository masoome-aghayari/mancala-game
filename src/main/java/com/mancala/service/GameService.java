package com.mancala.service;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.model.dto.GameDto;
import com.mancala.model.dto.PlayRequestModel;
import org.springframework.stereotype.Service;

@Service
public interface GameService {
    GameDto initializeNewGame();

    GameDto play(PlayRequestModel requestModel);
}
