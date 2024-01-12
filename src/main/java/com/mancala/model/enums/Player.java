package com.mancala.model.enums;

import lombok.Getter;

/*
 * @author masoome.aghayari
 * @since 1/11/24
 */
@Getter
public enum Player {
    PLAYER_A(1),
    PLAYER_B(2);
    private final int id;

    Player(int id) {
        this.id = id;
    }
}
