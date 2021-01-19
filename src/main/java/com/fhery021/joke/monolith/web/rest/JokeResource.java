package com.fhery021.joke.monolith.web.rest;

import com.fhery021.joke.monolith.domain.Joke;
import com.fhery021.joke.monolith.service.JokeService;
import com.fhery021.joke.monolith.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fhery021.joke.monolith.domain.Joke}.
 */
@RestController
@RequestMapping("/api")
public class JokeResource {

    private final Logger log = LoggerFactory.getLogger(JokeResource.class);

    private static final String ENTITY_NAME = "joke";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JokeService jokeService;

    public JokeResource(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    /**
     * {@code POST  /jokes} : Create a new joke.
     *
     * @param joke the joke to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new joke, or with status {@code 400 (Bad Request)} if the joke has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jokes")
    public ResponseEntity<Joke> createJoke(@Valid @RequestBody Joke joke) throws URISyntaxException {
        log.debug("REST request to save Joke : {}", joke);
        if (joke.getId() != null) {
            throw new BadRequestAlertException("A new joke cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Joke result = jokeService.save(joke);
        return ResponseEntity.created(new URI("/api/jokes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jokes} : Updates an existing joke.
     *
     * @param joke the joke to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated joke,
     * or with status {@code 400 (Bad Request)} if the joke is not valid,
     * or with status {@code 500 (Internal Server Error)} if the joke couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jokes")
    public ResponseEntity<Joke> updateJoke(@Valid @RequestBody Joke joke) throws URISyntaxException {
        log.debug("REST request to update Joke : {}", joke);
        if (joke.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Joke result = jokeService.save(joke);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, joke.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jokes} : get all the jokes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jokes in body.
     */
    @GetMapping("/jokes")
    public ResponseEntity<List<Joke>> getAllJokes(Pageable pageable) {
        log.debug("REST request to get a page of Jokes");
        Page<Joke> page = jokeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jokes/:id} : get the "id" joke.
     *
     * @param id the id of the joke to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the joke, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jokes/{id}")
    public ResponseEntity<Joke> getJoke(@PathVariable Long id) {
        log.debug("REST request to get Joke : {}", id);
        Optional<Joke> joke = jokeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(joke);
    }

    /**
     * {@code DELETE  /jokes/:id} : delete the "id" joke.
     *
     * @param id the id of the joke to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jokes/{id}")
    public ResponseEntity<Void> deleteJoke(@PathVariable Long id) {
        log.debug("REST request to delete Joke : {}", id);
        jokeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
