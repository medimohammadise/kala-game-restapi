package com.codebase.codechallenge.kalagame.rest;

import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.service.KalaGameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/games")
public class KalaGameResource {
   KalaGameService kalaGameService;
   public KalaGameResource(KalaGameService kalaGameService){
      this.kalaGameService = kalaGameService;

   }
   @RequestMapping(method=RequestMethod.POST)
   public long createGame(){
       return kalaGameService.createGame();
   }

   @RequestMapping(method=RequestMethod.PUT,value = "/{gameId}/pits/{pitId}")
   public MoveOutcomeDTO move(@PathVariable Integer gameId,@PathVariable String pitId){
      return kalaGameService.doMove(gameId,pitId);
   }

   @RequestMapping(method=RequestMethod.GET)
   public Map<Integer,Game> listAvailableGames(){
      return kalaGameService.listAvailableGames();
   }
}
