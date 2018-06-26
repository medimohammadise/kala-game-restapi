package com.codebase.codechallenge.kalagame.service;

import com.codebase.codechallenge.kalagame.domain.GameEntity;
import com.codebase.codechallenge.kalagame.dto.GameDTO;
import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.mapper.GameMapper;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.repository.KalaGameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KalaGameService {
    KalaGameRepository kalaGameRepository;
    GameMapper gameMapper;
    Map<Integer, GameDTO> gamePool=new HashMap<>();
    public KalaGameService(
            KalaGameRepository kalaGameRepository,
            GameMapper gameMapper
    ) {
        this.kalaGameRepository = kalaGameRepository;
        this.gameMapper=gameMapper;
        List<GameEntity> gameEntityList=kalaGameRepository.findAll();
        this.gamePool=gameEntityList.stream().collect(Collectors.toMap(x->x.getId(),x->gameMapper.gameToDTO(new Game(x))));
    }
    @Transactional
    public Integer createGame() {
        GameEntity gameEntity = kalaGameRepository.save(new GameEntity());
        Game game = new Game(gameEntity);
        gamePool.put(gameEntity.getId(),gameMapper.gameToDTO(game));
        return gameEntity.getId();
    }

    @Transactional
    public MoveOutcomeDTO doMove(Integer gameId, String pitId) {
        GameDTO gameDTO= gamePool.get(gameId);
        if (gameDTO==null) throw new RuntimeException("Game does not exists");  //TODO Not FOUND
        Game game=gameMapper.gameDToGame(gameDTO);
        int nextPlayerId=game.doMove(pitId);
        //TODO update model by using observer and then save the game
        MoveOutcomeDTO moveOutcomeDTO= new MoveOutcomeDTO(gameId,null,game.getStoneStatuse(),game.getCurrentPlayer(),nextPlayerId);
        //saveState(game);
        return moveOutcomeDTO;
    }

    private void saveState( Game game) {
        game.getGameEntity().setPits(game.getStoneStatuse());
        kalaGameRepository.save(game.getGameEntity());

    }
    public Map<Integer, GameDTO> listAvailableGames(){
        return gamePool;
    }

    public GameDTO getGame(int gameId){
        return gamePool.get(gameId);
    }

}
