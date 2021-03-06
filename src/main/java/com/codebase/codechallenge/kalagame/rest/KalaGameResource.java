package com.codebase.codechallenge.kalagame.rest;

import com.codebase.codechallenge.kalagame.dto.GameCreatedDTO;
import com.codebase.codechallenge.kalagame.dto.GameDTO;
import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.service.KalaGameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
@RequestMapping("/games")

public class KalaGameResource {
   KalaGameService kalaGameService;
   public KalaGameResource(KalaGameService kalaGameService){
      this.kalaGameService = kalaGameService;

   }
   @RequestMapping(method=RequestMethod.POST)
   public ResponseEntity<Resource<GameCreatedDTO>> createGame() throws URISyntaxException {
      int gameId= kalaGameService.createGame();
      URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gameId).toUri();
      Resource<GameCreatedDTO> gameCreatedDTOResource=new Resource<GameCreatedDTO>(new GameCreatedDTO(gameId));
      ControllerLinkBuilder link=linkTo(methodOn(this.getClass()).listAvailableGames().getBody());
       gameCreatedDTOResource.add(link.withRel("games"));
      return  ResponseEntity.created(location).body(gameCreatedDTOResource);
   }

   @RequestMapping(method=RequestMethod.PUT,value = "/{gameId}/pits/{pitId}")
   public ResponseEntity<MoveOutcomeDTO> move(@PathVariable Integer gameId, @PathVariable String pitId) throws URISyntaxException {
      return new ResponseEntity<> (kalaGameService.doMoveStones(gameId,pitId), HttpStatus.OK);
   }

   @RequestMapping(method=RequestMethod.GET)
   public ResponseEntity<List<GameDTO>> listAvailableGames(){

      List<GameDTO> allGames= kalaGameService.listAllAvailableGames();
      if (allGames.size()>0)
         return new ResponseEntity<>(allGames,HttpStatus.OK);
      else
         return new ResponseEntity<> (HttpStatus.NO_CONTENT);
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
   @RequestMapping(method=RequestMethod.DELETE)
   public ResponseEntity<GameDTO> deleteAllGame(){
      kalaGameService.deleteAll();
      return new ResponseEntity<> (HttpStatus.NO_CONTENT);
   }
}
