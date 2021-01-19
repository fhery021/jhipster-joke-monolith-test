package com.fhery021.joke.monolith.service;

import com.fhery021.joke.monolith.domain.Joke;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Joke}.
 */
public interface JokeService {

    /**
     * Save a joke.
     *
     * @param joke the entity to save.
     * @return the persisted entity.
     */
    Joke save(Joke joke);

    /**
     * Get all the jokes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Joke> findAll(Pageable pageable);


    /**
     * Get the "id" joke.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Joke> findOne(Long id);

    /**
     * Delete the "id" joke.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
