package com.codebase.codechallenge.kalagame.rest;

import com.codebase.codechallenge.kalagame.dto.GameCreatedDTO;
import com.codebase.codechallenge.kalagame.dto.GameDTO;
import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.service.KalaGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class KalaGameResource {
   KalaGameService kalaGameService;
   public KalaGameResource(KalaGameService kalaGameService){
      this.kalaGameService = kalaGameService;

   }
   @RequestMapping(method=RequestMethod.POST)
   public ResponseEntity<GameCreatedDTO> createGame() throws URISyntaxException {
      int gameId= kalaGameService.createGame();
      return new ResponseEntity<> (new GameCreatedDTO (gameId,new URI("/games/"+String.valueOf(gameId))),HttpStatus.CREATED);
   }

   @RequestMapping(method=RequestMethod.PUT,value = "/{gameId}/pits/{pitId}")
   public ResponseEntity<MoveOutcomeDTO> move(@PathVariable Integer gameId, @PathVariable String pitId) throws URISyntaxException {
      return new ResponseEntity<> (kalaGameService.doMove(gameId,pitId), HttpStatus.OK);
   }

   @RequestMapping(method=RequestMethod.GET)
   public Map<Integer,GameDTO> listAvailableGames(){
      return kalaGameService.listAvailableGames();
   }

   @RequestMapping(method=RequestMethod.GET,value="/{gameId}")
   public ResponseEntity<Optional<GameDTO>> getGame(@PathVariable Integer gameId){
      Optional<GameDTO> gameDTO=kalaGameService.getGame(gameId);
      if (gameDTO.isPresent())
         return new ResponseEntity<>(gameDTO,HttpStatus.OK);
      else
         return new ResponseEntity<> (HttpStatus.NO_CONTENT);
   }

   @RequestMapping(method=RequestMethod.DELETE,value="/{gameId}")
   public ResponseEntity<GameDTO> deleteGame(@PathVariable Integer gameId){
      kalaGameService.delete(gameId);
      return new ResponseEntity<> (HttpStatus.NO_CONTENT);
   }
}
