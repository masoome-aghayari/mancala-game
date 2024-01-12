package com.mancala.service;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.model.dto.GameDto;
import org.springframework.stereotype.Service;

@Service
public interface GameService {
    GameDto initializeNewGame();
}
