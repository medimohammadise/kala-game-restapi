package com.codebase.codechallenge.kalagame.mapper;

import com.codebase.codechallenge.kalagame.domain.GameEntity;
import com.codebase.codechallenge.kalagame.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameEntityMapper {

    @Mapping(target="gameId",source="gameEntity.id")
    @Mapping(target="game.board.pits",source="gameEntity.pits")
    Game gameEntityToGame(GameEntity gameEntity);

    List<Game> gameEntityListToGameList(List<GameEntity> gameEntityList);

    @Mapping(target="id",source="game.gameId")
    @Mapping(target="gameEntity.pits",source="game.board.pits")
    GameEntity gameToGameEntity(Game game);
}
