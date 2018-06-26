package com.codebase.codechallenge.kalagame;

import com.codebase.codechallenge.kalagame.dto.GameDTO;
import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.mapper.GameMapper;
import com.codebase.codechallenge.kalagame.model.Game;
import com.codebase.codechallenge.kalagame.rest.KalaGameResource;
import com.codebase.codechallenge.kalagame.service.KalaGameService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
public class KalaGameRESTApplicationTests {

	@Test
	public void contextLoads() {
	}
	static int createGameId;
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
		MvcResult result= restKalaGameMockMvc.perform(post("/games")).andExpect(status().isCreated()).andDo(print()).andReturn();
		JSONObject jsonObject=new  JSONObject(result.getResponse().getContentAsString());
		createGameId=jsonObject.getInt("id");
	}


	private int chooseRandomPitForPlayer(Game game, int playerNumber){
		List<Integer> availablePitsForMove=game.getBoard().getAvailablePitsForMove(playerNumber);
		Random random = new Random();
		return availablePitsForMove.get(random.nextInt(availablePitsForMove.size()));

	}
	@Test
	public void playGameUntilWin() throws Exception {
		int currentPlayer=0;
		MoveOutcomeDTO moveOutcomeDTO=null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		MvcResult result=restKalaGameMockMvc.perform(get("/games/"+createGameId)).andExpect(status().isOk()).andDo(print()).andReturn();
		GameDTO gameDTO=mapper.readValue(result.getResponse().getContentAsString(),GameDTO.class);
		Game game=gameMapper.gameDToGame(gameDTO);

       while(!game.getBoard().isGameOver()) {
            int selectedPit = chooseRandomPitForPlayer(game, currentPlayer);
            result = restKalaGameMockMvc.perform(put("/games/" + createGameId + "/pits/" + selectedPit)).andExpect(status().isOk()).andDo(print()).andReturn();
		   	moveOutcomeDTO= mapper.readValue(result.getResponse().getContentAsString(), MoveOutcomeDTO.class);
            currentPlayer = moveOutcomeDTO.getNextPlayerTurn();
        }
	}


}
