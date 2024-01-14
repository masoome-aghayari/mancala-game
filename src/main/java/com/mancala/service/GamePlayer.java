package com.mancala.service;

/*
 * @author masoome.aghayari
 * @since 1/11/24
 */

import com.mancala.model.dto.GameDto;
import com.mancala.model.enums.Player;
import com.mancala.model.exceptions.EmptyPocketException;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Data
@Component
public class GamePlayer {

    private static final int POCKETS_COUNT = 6;
    private static final int PLAYER_A_FIRST_INDEX = 0;
    private static final int PLAYER_B_FIRST_INDEX = 7;
    private static final int PLAYER_A_LAST_INDEX = PLAYER_A_FIRST_INDEX + POCKETS_COUNT;
    private static final int PLAYER_B_LAST_INDEX = PLAYER_B_FIRST_INDEX + POCKETS_COUNT;

    private GameDto game;
    private int stones;

    public void play(int pocketIndex) {
        checkPocketOwner(pocketIndex);
        int[] board = game.getBoard();
        stones = board[pocketIndex];
        checkPocketEmptiness();
        board[pocketIndex] = 0;
        int playerFirstIndex = getPlayerFirstIndex();
        int playerLastIndex = playerFirstIndex + POCKETS_COUNT;
        while (stones > 0) {
            addStonesToRightPockets(pocketIndex, playerLastIndex);
            addStonesToRivalPockets(playerLastIndex);
            addStonesToLeftPockets(playerFirstIndex, pocketIndex);
        }
    }

    private void checkPocketOwner(int pocketIndex) {
        var currentPlayer = game.getCurrentPlayer();
        if (!isPlayerPocket(currentPlayer, pocketIndex))
            throw new IllegalArgumentException("choose from your own pockets");
    }

    private static boolean isPlayerPocket(Player currentPlayer, int pocketIndex) {
        return (currentPlayer == Player.PLAYER_A && pocketIndex < PLAYER_A_LAST_INDEX)
               || (currentPlayer == Player.PLAYER_B &&
                   pocketIndex >= PLAYER_B_FIRST_INDEX && pocketIndex < PLAYER_B_LAST_INDEX);
    }

    private void checkPocketEmptiness() {
        if (stones == 0)
            throw new EmptyPocketException("you can't choose an empty pocket");
    }

    private int getPlayerFirstIndex() {
        return game.getCurrentPlayer().equals(Player.PLAYER_A) ? PLAYER_A_FIRST_INDEX : PLAYER_B_FIRST_INDEX;
    }

    private void addStonesToRightPockets(int pocketIndex, int playerLastIndex) {
        var fromIndex = pocketIndex + 1;
        var toIndex = Math.min(playerLastIndex, pocketIndex + stones);
        addStonesToPockets(fromIndex, toIndex);
    }

    private void addStonesToRivalPockets(int playerLastIndex) {
        if (stones > 0) {
            int rivalLastIndex = getRivalLastIndex(playerLastIndex);
            int rivalFirstIndex = rivalLastIndex - POCKETS_COUNT;
            int toIndex = Math.min(rivalLastIndex - 1, rivalFirstIndex + stones - 1);
            addStonesToPockets(rivalFirstIndex, toIndex);
        }
    }

    private int getRivalLastIndex(int playerLastIndex) {
        return (game.getBoard().length - 1) - playerLastIndex + POCKETS_COUNT;
    }

    private Player getRival() {
        return game.getCurrentPlayer() == Player.PLAYER_A ? Player.PLAYER_B : Player.PLAYER_A;
    }

    private void addStonesToLeftPockets(int playerFirstIndex, int pocketIndex) {
        if (stones > 0) {
            int toIndex;
            var leftPocketsCount = pocketIndex - playerFirstIndex;
            toIndex = getToIndexForAddingStonesToLeftPockets(playerFirstIndex, pocketIndex, leftPocketsCount);
            addStonesToPockets(playerFirstIndex, toIndex);
        }
    }

    private int getToIndexForAddingStonesToLeftPockets(int playerFirstIndex, int pocketIndex, int leftPocketsCount) {
        int toIndex;
        if (stones == 1 || leftPocketsCount == 0) toIndex = playerFirstIndex;
        else {
            toIndex = stones > leftPocketsCount ? pocketIndex : stones == leftPocketsCount ? pocketIndex - 1 :
                    playerFirstIndex + stones - 1;
        }
        return toIndex;
    }

    private void addStonesToPockets(int fromIndex, int toIndex) {
        var playerLastIndex = getPlayerLastIndex();
        doAddStonesToPockets(fromIndex, toIndex);
        if (stones == 0 && toIndex != playerLastIndex)
            game.setCurrentPlayer(getRival());
    }

    private void doAddStonesToPockets(int fromIndex, int toIndex) {
        var board = game.getBoard();
        IntStream.rangeClosed(fromIndex, toIndex).forEach(i -> {
            if (stones == 1 && board[i] == 0 && isPlayerPocket(i)) {
                addCrossIndexStonesToPlayerStore(i);
                board[getPlayerLastIndex()]++;
            } else {
                board[i] += 1;
                stones--;
            }
        });
    }

    private boolean isPlayerPocket(int index) {
        return game.getCurrentPlayer() == Player.PLAYER_A ? index < 6 : index >= 7 && index < 13;
    }

    private void addCrossIndexStonesToPlayerStore(int i) {
        var playerLastIndex = getPlayerLastIndex();
        if (i < playerLastIndex) {
            var board = game.getBoard();
            var crossIndex = board.length - i - 2;
            board[playerLastIndex] += board[crossIndex];
            board[crossIndex] = 0;
            stones = 0;
        }
    }

    private int getPlayerLastIndex() {
        return game.getCurrentPlayer().equals(Player.PLAYER_A) ? PLAYER_A_LAST_INDEX : PLAYER_B_LAST_INDEX;
    }

    public void findWinner() {
        collectRemainedStones();
        var board = game.getBoard();
        int playerAStones = board[PLAYER_A_LAST_INDEX];
        int playerBStones = board[PLAYER_B_LAST_INDEX];
        setWinner(playerAStones, playerBStones);
    }

    public boolean isGameOver() {
        return isAllPocketsEmpty(PLAYER_A_FIRST_INDEX, PLAYER_A_LAST_INDEX)
               || isAllPocketsEmpty(PLAYER_B_FIRST_INDEX, PLAYER_B_LAST_INDEX);
    }

    private boolean isAllPocketsEmpty(int fromIndex, int toIndex) {
        var board = game.getBoard();
        return IntStream.range(fromIndex, toIndex).allMatch(i -> board[i] == 0);
    }

    private void collectRemainedStones() {
        var rivalFirstIndex = isAllPocketsEmpty(PLAYER_A_FIRST_INDEX, PLAYER_A_LAST_INDEX) ? PLAYER_B_FIRST_INDEX
                : PLAYER_A_FIRST_INDEX;
        putAllRivalStonesToItsStore(rivalFirstIndex);
    }

    private void putAllRivalStonesToItsStore(int rivalFirstIndex) {
        var rivalLastIndex = rivalFirstIndex + POCKETS_COUNT;
        var board = game.getBoard();
        IntStream.range(rivalFirstIndex, rivalLastIndex).filter(i -> board[i] != 0).forEach(i -> {
            board[rivalLastIndex] += board[i];
            board[i] = 0;
        });
    }

    private void setWinner(int playerAStones, int playerBStones) {
        if (playerAStones > playerBStones)
            game.setWinner(Player.PLAYER_A.name());
        else if (playerBStones > playerAStones)
            game.setWinner(Player.PLAYER_B.name());
    }
}