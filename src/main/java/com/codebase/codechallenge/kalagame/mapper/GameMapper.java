package com.codebase.codechallenge.kalagame.mapper;

import com.codebase.codechallenge.kalagame.dto.BoardDTO;
import com.codebase.codechallenge.kalagame.dto.GameDTO;
import com.codebase.codechallenge.kalagame.dto.PlayerDTO;
import com.codebase.codechallenge.kalagame.model.Board;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {
    @Mapping(target="boardDTO",source="game.board")
    GameDTO gameToDTO(Game game);

    @Mapping(target="game.board",source="boardDTO")
    Game gameDToGame(GameDTO gameDTO);

    PlayerDTO palyerToDTO(Player player);
    PlayerDTO[] palyersToDTOs(Player[] player);

    Player palyerDTOToPlayer(PlayerDTO playerDTO);
    Player[] palyersDTOToPlayers(PlayerDTO[] playersDTO);

    BoardDTO boardToBoradDTO(Board board);
    Board boardDTOToBorad(BoardDTO board);
}
