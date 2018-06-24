package com.codebase.codechallenge.kalagame.domain;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Map;

@Entity
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "pit_key")
    @Column(name = "pit_value")
    @BatchSize(size = 14)
    Map<String,Integer> pits;

    public GameEntity() {
    }

    public GameEntity(Integer id, Map<String,Integer>  pits) {
        this.id = id;
        this.pits = pits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Integer> getPits() {
        return pits;
    }

    public void setPits(Map<String, Integer> pits) {
        this.pits = pits;
    }
}
