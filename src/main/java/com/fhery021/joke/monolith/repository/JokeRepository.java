package com.fhery021.joke.monolith.repository;

import com.fhery021.joke.monolith.domain.Joke;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Joke entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
}
