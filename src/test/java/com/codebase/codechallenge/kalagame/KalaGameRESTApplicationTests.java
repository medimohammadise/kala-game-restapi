package com.codebase.codechallenge.kalagame;

import com.codebase.codechallenge.kalagame.dto.GameDTO;
import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.mapper.GameMapper;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.rest.KalaGameResource;
import com.codebase.codechallenge.kalagame.service.KalaGameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class KalaGameRESTApplicationTests {
	Logger log= LoggerFactory.getLogger(getClass());
	//set value = -1 to create new game and play until win or set your gameId to play
	static int createGameId=3713;
	@Autowired
	KalaGameService kalaGameService;

	@Autowired
	GameMapper gameMapper;


	MockMvc restKalaGameMockMvc;


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		KalaGameResource kalaGameResource = new KalaGameResource(kalaGameService);
		this.restKalaGameMockMvc = MockMvcBuilders.standaloneSetup(kalaGameResource)
				.build();
	}

	@Test
	public void createGame() throws Exception {
		if (createGameId==-1) {
			MvcResult result = restKalaGameMockMvc.perform(post("/games")).andExpect(status().isCreated()).andDo(print()).andReturn();
			JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
			createGameId = jsonObject.getInt("id");
		}
	}


	private String chooseRandomPitForPlayer(Game game, int playerNumber){
		List<String> availablePitsForMove=game.getBoard().getAvailablePitsForMove(playerNumber);
		if (availablePitsForMove.size()>0) {
			Random random = new Random();
			return availablePitsForMove.get(random.nextInt(availablePitsForMove.size()));
		}
		else
			return "";

	}

	@Test
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	@Commit   // I do not want Junit to rollback my transaction I want to play and win and check the result
	public void playGameUntilWin() throws Exception {
		int currentPlayer=0;
		MoveOutcomeDTO moveOutcomeDTO=null;
		ObjectMapper mapper = new ObjectMapper();
		Game game=null;
		MvcResult result=null;
		GameDTO gameDTO=null;
		result=restKalaGameMockMvc.perform(get("/games/"+createGameId)).andExpect(status().isOk()).andDo(print()).andReturn();
		gameDTO=mapper.readValue(result.getResponse().getContentAsString(),GameDTO.class);
		game=gameMapper.gameDToGame(gameDTO);
       do {

            String selectedPit = chooseRandomPitForPlayer(game, currentPlayer);
		     log.info("selected pit ="+selectedPit );
            result = restKalaGameMockMvc.perform(put("/games/" + createGameId + "/pits/" + selectedPit)).andExpect(status().isOk()).andDo(print()).andReturn();
		   	moveOutcomeDTO= mapper.readValue(result.getResponse().getContentAsString(), MoveOutcomeDTO.class);
            currentPlayer = moveOutcomeDTO.getNextPlayerTurn();
		   result=restKalaGameMockMvc.perform(get("/games/"+createGameId)).andExpect(status().isOk()).andDo(print()).andReturn();
		   gameDTO=mapper.readValue(result.getResponse().getContentAsString(),GameDTO.class);
		   game=gameMapper.gameDToGame(gameDTO);
        }
        while((!game.getBoard().isGameOver()));
	}


}
