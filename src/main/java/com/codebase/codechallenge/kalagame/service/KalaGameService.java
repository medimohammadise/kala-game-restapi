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
        Map<String, Integer> pits = new TreeMap<>();
        IntStream.range(1, 14).forEach(i -> pits.put(String.valueOf(i), 6));
        pits.put("7", 0);
        pits.put("14", 0);
        pits.entrySet().stream().forEach(e->System.out.println(e.getKey()+" - > "+e.getValue()));
        gameEntity.setPits(pits);
        kalaGameRepository.save(gameEntity);
        Game game = new Game(gameEntity.getId(),gameEntity.getPits());
        return gameEntity.getId();
    }

    @Transactional
    public MoveOutcomeDTO doMove(int gameId, String selectedPitId) throws URISyntaxException {
        Optional<GameEntity> gameEntity=kalaGameRepository.findById(gameId);
        if (!gameEntity.isPresent()) throw new IllegalArgumentException("Game does not exists");
        Game game=gameEntityMapper.gameEntityToGame(gameEntity.get());
        game.doMove(selectedPitId);
        saveState(game);
        MoveOutcomeDTO moveOutcomeDTO= new MoveOutcomeDTO(gameMapper.gameToDTO(game),selectedPitId);
        log.info("Next player DTO "+gameMapper.gameToDTO(game).getNextPlayer());
        return moveOutcomeDTO;
    }

    public List<GameDTO> listAvailableGames(){
        return gameMapper.gameListToDTOList(gameEntityMapper.gameEntityListToGameList(kalaGameRepository.findAll()));
    }

    public Optional<GameDTO> getGame(int gameId){
        //Optional<GameDTO> gameDTO=Optional.ofNullable(gamePool.get(gameId));
        Optional<GameEntity> gameEntity=kalaGameRepository.findById(gameId);
        if (gameEntity.isPresent()) {
            Game game = new Game(gameEntity.get().getId(), gameEntity.get().getPits());
            return Optional.of(gameMapper.gameToDTO(game));
        }
        else
            return Optional.empty();
    }

    private void saveState(Game game){
        GameEntity gameEntity=gameEntityMapper.gameToGameEntity(game);
        gameEntity.getPits().entrySet().forEach(entry->{
            if (game.getBoard().getPits().get(entry.getKey())!=entry.getValue())
                throw new RuntimeException("saving could not be happen");
        });
        kalaGameRepository.save(gameEntity);

    }

    public void delete(int gameId) {
        GameEntity gameEntity= kalaGameRepository.findById(gameId).get();
        if (gameEntity!=null) {
            kalaGameRepository.delete(gameEntity);
        }
        else
            throw new IllegalArgumentException("Game does not exists");
    }

    @Transactional
    public void deleteAll() {
        kalaGameRepository.findAll().stream().forEach(game->kalaGameRepository.delete(game));
    }
}
