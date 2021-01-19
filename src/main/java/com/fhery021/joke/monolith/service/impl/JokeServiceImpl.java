package com.fhery021.joke.monolith.service.impl;

import com.fhery021.joke.monolith.service.JokeService;
import com.fhery021.joke.monolith.domain.Joke;
import com.fhery021.joke.monolith.repository.JokeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Joke}.
 */
@Service
@Transactional
public class JokeServiceImpl implements JokeService {

    private final Logger log = LoggerFactory.getLogger(JokeServiceImpl.class);

    private final JokeRepository jokeRepository;

    public JokeServiceImpl(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public Joke save(Joke joke) {
        log.debug("Request to save Joke : {}", joke);
        return jokeRepository.save(joke);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Joke> findAll(Pageable pageable) {
        log.debug("Request to get all Jokes");
        return jokeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Joke> findOne(Long id) {
        log.debug("Request to get Joke : {}", id);
        return jokeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Joke : {}", id);
        jokeRepository.deleteById(id);
    }
}
