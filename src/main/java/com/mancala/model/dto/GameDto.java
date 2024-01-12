package com.mancala.model.dto;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.model.enums.GameStatus;
import com.mancala.model.enums.Player;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GameDto {
    private UUID id;
    private int[] board = new int[14];
    private Player currentPlayer;
    private GameStatus status;
    private String winner;
}
