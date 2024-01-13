package com.mancala.model.entity;

/*
 * @author masoome.aghayari
 * @since 1/11/24
 */

import com.mancala.model.enums.GameStatus;
import com.mancala.model.enums.Player;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "MANCALA_GAME")
@Data
@Cacheable(value = false)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Basic
    private int[] board = new int[14];

    @Enumerated(EnumType.STRING)
    private Player currentPlayer;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private String winner;

    public Game() {
        Arrays.fill(board, 0, 6, 6);
        Arrays.fill(board, 7, 13, 6);
        board[6] = 0;
        board[13] = 0;
        currentPlayer = Player.PLAYER_A;
        status = GameStatus.IN_PROGRESS;
    }
}
