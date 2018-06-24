package com.codebase.codechallenge.kalagame.service;

import com.codebase.codechallenge.kalagame.domain.GameEntity;
import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
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
    Map<Integer, Game> gamePool=new HashMap<>();
    public KalaGameService(
            KalaGameRepository kalaGameRepository
    ) {
        this.kalaGameRepository = kalaGameRepository;
        List<GameEntity> gameEntityList=kalaGameRepository.findAll();
        this.gamePool=gameEntityList.stream().collect(Collectors.toMap(x->x.getId(),x->new Game(x)));
    }
    @Transactional
    public Integer createGame() {
        GameEntity gameEntity = kalaGameRepository.save(new GameEntity());
        Game game = new Game(gameEntity);
        gamePool.put(gameEntity.getId(),game);
        return gameEntity.getId();
    }

    @Transactional
    public MoveOutcomeDTO doMove(Integer gameId, String pitId) {
        Game game= gamePool.get(gameId);
        if (game==null) throw new RuntimeException("Game does not exists");  //TODO Not FOUND
        int nextPlayerId=game.doMove(pitId);
        //TODO update model by using observer and then save the game
        MoveOutcomeDTO moveOutcomeDTO= new MoveOutcomeDTO(gameId,null,game.getStoneStatuse(),game.getCurrentPlayerId(),nextPlayerId);
        //saveState(game);
        return moveOutcomeDTO;
    }

    private void saveState( Game game) {
        game.getGameEntity().setPits(game.getStoneStatuse());
        kalaGameRepository.save(game.getGameEntity());

    }
    public Map<Integer, Game> listAvailableGames(){
        return gamePool;
    }

    public Game getGame(int gameId){
        return gamePool.get(gameId);
    }

}
