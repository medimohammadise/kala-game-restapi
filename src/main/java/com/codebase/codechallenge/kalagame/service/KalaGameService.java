package com.codebase.codechallenge.kalagame.service;

import com.codebase.codechallenge.kalagame.domain.GameEntity;
import com.codebase.codechallenge.kalagame.dto.GameDTO;
import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.mapper.GameMapper;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.repository.KalaGameRepository;
import com.codebase.codechallenge.kalagame.utils.NumberStringComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class KalaGameService {
    Logger log= LoggerFactory.getLogger(getClass());
    KalaGameRepository kalaGameRepository;
    GameMapper gameMapper;
    Map<Integer, GameDTO> gamePool=new HashMap<>();
    public KalaGameService(
            KalaGameRepository kalaGameRepository,
            GameMapper gameMapper
    ) {
        this.kalaGameRepository = kalaGameRepository;
        this.gameMapper=gameMapper;
        refreshGamingPool();
    }

    @Transactional
    public Integer createGame() {
        GameEntity gameEntity=new GameEntity();
        Map<String, Integer> pits = new TreeMap<>();
        IntStream.range(1, 14).forEach(i -> pits.put(String.valueOf(i), 6));
        pits.put("7", 0);
        pits.put("14", 0);
        pits.entrySet().stream().forEach(e->System.out.println(e.getKey()+" - > "+e.getValue()));
        gameEntity.setPits(pits);
        kalaGameRepository.save(gameEntity);
        Game game = new Game(gameEntity.getId(),gameEntity.getPits());
        gamePool.put(gameEntity.getId(),gameMapper.gameToDTO(game));
        return gameEntity.getId();
    }

    @Transactional
    public MoveOutcomeDTO doMove(Integer gameId, String pitId) throws URISyntaxException {
        GameDTO gameDTO= gamePool.get(gameId);

        if (gameDTO==null) throw new RuntimeException("Game does not exists");  //TODO Not FOUND
        Game game=gameMapper.gameDToGame(gameDTO);
        game.doMove(pitId);
        saveState(game);
        MoveOutcomeDTO moveOutcomeDTO= new MoveOutcomeDTO(gameId,game.getStoneStatuse(),game.getCurrentPlayer(),game.getNextPlayer(),pitId);
        this.gamePool.put(game.getGameId(),gameMapper.gameToDTO(game));
        log.info("Next player DTO "+gameMapper.gameToDTO(game).getNextPlayer());
        return moveOutcomeDTO;
    }

    public Map<Integer, GameDTO> listAvailableGames(){
        return gamePool;
    }

    public Optional<GameDTO> getGame(int gameId){
        Optional<GameDTO> gameDTO=Optional.ofNullable(gamePool.get(gameId));
        return gameDTO;
    }

    public void delete(int gameId) {
        GameEntity gameEntity= kalaGameRepository.findById(gameId).get();
        kalaGameRepository.delete(gameEntity);
        refreshGamingPool();
    }

    private void saveState( Game game) {
        GameEntity gameEntity= kalaGameRepository.findById(game.getGameId()).get();
        gameEntity.setPits(game.getBoard().getPits());
        kalaGameRepository.save(gameEntity);

    }
    private void refreshGamingPool(){
        List<GameEntity> gameEntityList=kalaGameRepository.findAll();
        this.gamePool=gameEntityList.stream().collect(Collectors.toMap(x->x.getId(),x->gameMapper.gameToDTO(new Game(x.getId(),x.getPits()))));
    }
}
