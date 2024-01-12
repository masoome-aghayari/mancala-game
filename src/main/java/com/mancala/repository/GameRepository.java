package com.mancala.repository;

import com.mancala.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
}
