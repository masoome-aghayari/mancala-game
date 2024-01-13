package com.mancala.model.dto;

/*
 * @author masoome.aghayari
 * @since 1/11/24
 */

import com.mancala.model.annotation.PocketIndex;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PlayRequestModel {

    @PocketIndex(notAllowedValue = 6)
    private int pocketIndex;

    @NotNull
    private UUID gameId;
}
