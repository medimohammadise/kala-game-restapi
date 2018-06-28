package com.codebase.codechallenge.kalagame;

import com.codebase.codechallenge.kalagame.dto.MoveOutcomeDTO;
import com.codebase.codechallenge.kalagame.exception.RestResponseEntityExceptionHandler;
import com.codebase.codechallenge.kalagame.mapper.GameMapper;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
  This class test unexpected values to check how service handling incorrect parameters
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class KalaGameRESTAPITest {
    Logger log = LoggerFactory.getLogger(getClass());
    //set value = -1 to create new game and play until win or set your gameId to play
    static int createGameId = -1;
    @Autowired
    KalaGameService kalaGameService;

    @Autowired
    GameMapper gameMapper;


    MockMvc restKalaGameMockMvc;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KalaGameResource kalaGameResource = new KalaGameResource(kalaGameService);
        this.restKalaGameMockMvc = MockMvcBuilders.standaloneSetup(kalaGameResource).setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Commit   // I do not want Junit to rollback my transaction I want to play and win and check the result
    public void createGame() throws Exception {
        if (createGameId == -1) {
            MvcResult result = restKalaGameMockMvc.perform(post("/games")).andExpect(status().isCreated()).andDo(print()).andReturn();
            JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
            createGameId = jsonObject.getInt("id");
        }
    }


    @Test
    // I do not want Junit to rollback my transaction I want to play and win and check the result
    public void playWithWrongPit() throws Exception {
            String selectedPit="6";
            ObjectMapper mapper = new ObjectMapper();
            log.info("selected pit =" + selectedPit);
            MvcResult result = restKalaGameMockMvc.perform(put("/games/" + createGameId + "/pits/" + selectedPit)).andExpect(status().isOk()).andDo(print()).andReturn();
            MoveOutcomeDTO moveOutcomeDTO = mapper.readValue(result.getResponse().getContentAsString(), MoveOutcomeDTO.class);
            int nextPlayer = moveOutcomeDTO.getNextPlayerTurn();
            //intentionally choose wrong player
            if (nextPlayer==1)
                selectedPit="2";
            else
                selectedPit="13";

        restKalaGameMockMvc.perform(put("/games/" + createGameId + "/pits/" + selectedPit)).andExpect(status().isConflict())
                .andExpect(content().string(containsString("It is not your turn, it is player")));



    }


}
