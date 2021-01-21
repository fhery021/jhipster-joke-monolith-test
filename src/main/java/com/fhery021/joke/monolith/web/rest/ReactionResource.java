package com.fhery021.joke.monolith.web.rest;

import com.fhery021.joke.monolith.domain.Reaction;
import com.fhery021.joke.monolith.repository.ReactionRepository;
import com.fhery021.joke.monolith.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fhery021.joke.monolith.domain.Reaction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReactionResource {

    private final Logger log = LoggerFactory.getLogger(ReactionResource.class);

    private static final String ENTITY_NAME = "reaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReactionRepository reactionRepository;

    public ReactionResource(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    /**
     * {@code POST  /reactions} : Create a new reaction.
     *
     * @param reaction the reaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reaction, or with status {@code 400 (Bad Request)} if the reaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reactions")
    public ResponseEntity<Reaction> createReaction(@Valid @RequestBody Reaction reaction) throws URISyntaxException {
        log.debug("REST request to save Reaction : {}", reaction);
        if (reaction.getId() != null) {
            throw new BadRequestAlertException("A new reaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reaction result = reactionRepository.save(reaction);
        return ResponseEntity.created(new URI("/api/reactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reactions} : Updates an existing reaction.
     *
     * @param reaction the reaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reaction,
     * or with status {@code 400 (Bad Request)} if the reaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reactions")
    public ResponseEntity<Reaction> updateReaction(@Valid @RequestBody Reaction reaction) throws URISyntaxException {
        log.debug("REST request to update Reaction : {}", reaction);
        if (reaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reaction result = reactionRepository.save(reaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reactions} : get all the reactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reactions in body.
     */
    @GetMapping("/reactions")
    public List<Reaction> getAllReactions() {
        log.debug("REST request to get all Reactions");
        return reactionRepository.findAll();
    }

    /**
     * {@code GET  /reactions/:id} : get the "id" reaction.
     *
     * @param id the id of the reaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reactions/{id}")
    public ResponseEntity<Reaction> getReaction(@PathVariable Long id) {
        log.debug("REST request to get Reaction : {}", id);
        Optional<Reaction> reaction = reactionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reaction);
    }

    /**
     * {@code DELETE  /reactions/:id} : delete the "id" reaction.
     *
     * @param id the id of the reaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reactions/{id}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long id) {
        log.debug("REST request to delete Reaction : {}", id);
        reactionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
