package com.codebase.codechallenge.kalagame.repository;

import com.codebase.codechallenge.kalagame.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;




@org.springframework.stereotype.Repository
public interface KalaGameRepository extends JpaRepository<GameEntity,Integer> {

}
