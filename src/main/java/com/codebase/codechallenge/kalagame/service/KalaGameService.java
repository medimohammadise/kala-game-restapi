package com.codebase.codechallenge.kalagame.service;

import com.codebase.codechallenge.kalagame.domain.GameEntity;
import com.codebase.codechallenge.kalagame.dto.GameDTO;
import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.mapper.GameEntityMapper;
import com.codebase.codechallenge.kalagame.mapper.GameMapper;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.repository.KalaGameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.IntStream;

@Service
public class KalaGameService {
    Logger log= LoggerFactory.getLogger(getClass());
    KalaGameRepository kalaGameRepository;
    GameMapper gameMapper;
    GameEntityMapper gameEntityMapper;
    public KalaGameService(
            KalaGameRepository kalaGameRepository,
            GameMapper gameMapper,
            GameEntityMapper gameEntityMapper
    ) {
        this.kalaGameRepository = kalaGameRepository;
        this.gameMapper=gameMapper;
        this.gameEntityMapper=gameEntityMapper;
    }

    @Transactional
    public Integer createGame() {
        GameEntity gameEntity=new GameEntity();
        gameEntity.setPits(prepareAndInitializePits());
        kalaGameRepository.save(gameEntity);
        return gameEntity.getId();
    }

    private  Map<String, Integer> prepareAndInitializePits(){
        Map<String, Integer> pits = new TreeMap<>();
        //prepare and initialize pits
        IntStream.range(1, 14).forEach(i -> pits.put(String.valueOf(i), 6));
        pits.put("7", 0); //house for player 1
        pits.put("14", 0);//house for player 2
        return pits;
    }

    @Transactional
    public MoveOutcomeDTO doMoveStones(int gameId, String selectedPitId) throws URISyntaxException {
        Optional<GameEntity> gameEntity=kalaGameRepository.findById(gameId);
        if (!gameEntity.isPresent()) throw new IllegalArgumentException("Game does not exists");

        //get business Game Model
        Game game=gameEntityMapper.gameEntityToGame(gameEntity.get());
        game.doMoveStones(selectedPitId);
        saveGameState(game);
        MoveOutcomeDTO moveOutcomeDTO= new MoveOutcomeDTO(gameMapper.gameToDTO(game),selectedPitId);
        return moveOutcomeDTO;
    }

    public List<GameDTO> listAllAvailableGames(){
        return gameMapper.gameListToDTOList(gameEntityMapper.gameEntityListToGameList(kalaGameRepository.findAll()));
    }

    /*
         gets's game by gameId
     */
    public Optional<GameDTO> getGame(int gameId){
        //Optional<GameDTO> gameDTO=Optional.ofNullable(gamePool.get(gameId));
        Optional<GameEntity> gameEntity=kalaGameRepository.findById(gameId);
        if (gameEntity.isPresent()) {
            Game game = gameEntityMapper.gameEntityToGame(gameEntity.get());
            return Optional.of(gameMapper.gameToDTO(game));
        }
        else
            return Optional.empty();
    }

    /*
       saves Game business model state in database
    */
    private void saveGameState(Game game){
        GameEntity gameEntity=gameEntityMapper.gameToGameEntity(game);
        kalaGameRepository.save(gameEntity);

    }

    /*
           delete game from database by gameId
    */
    public void delete(int gameId) {
        GameEntity gameEntity= kalaGameRepository.findById(gameId).get();
        if (gameEntity!=null) {
            kalaGameRepository.delete(gameEntity);
        }
        else
            throw new IllegalArgumentException("Game does not exists");
    }

    /*
              cleanup all games in database
     */
    @Transactional
    public void deleteAll() {
        kalaGameRepository.findAll().stream().forEach(game->kalaGameRepository.delete(game));
    }
}
