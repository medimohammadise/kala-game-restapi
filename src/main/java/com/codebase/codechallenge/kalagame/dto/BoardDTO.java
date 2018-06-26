package com.codebase.codechallenge.kalagame.dto;

import java.util.HashMap;
import java.util.Map;

public class BoardDTO {
    private Map<String, Integer> pits=new HashMap<>();

    public BoardDTO() {
    }

    public Map<String, Integer> getPits() {
        return pits;
    }

    public void setPits(Map<String, Integer> pits) {
        this.pits = pits;
    }

}
