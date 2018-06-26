package com.codebase.codechallenge.kalagame.domain;

import com.codebase.codechallenge.kalagame.utils.NumberStringComparator;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

@Entity
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="game_pits",
            joinColumns=@JoinColumn(name="game_id"))
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Integer> getPits() {
        return pits;
    }

    public void setPits(Map<String, Integer> pits) {
        this.pits = pits;
    }
}
