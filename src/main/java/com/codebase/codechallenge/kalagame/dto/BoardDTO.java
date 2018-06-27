package com.codebase.codechallenge.kalagame.dto;

import java.util.Map;
import java.util.TreeMap;

public class BoardDTO {
    private Map<String, Integer> pits=new TreeMap<>();

    public BoardDTO() {
    }

    public Map<String, Integer> getPits() {
        return pits;
    }

    public void setPits(Map<String, Integer> pits) {
        this.pits = pits;
    }

}
