package com.mancala.mapper;

import com.mancala.model.dto.GameDto;
import com.mancala.model.entity.Game;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameDto entityToDto(Game entity);

    Game dtoToEntity(GameDto dto);
}
