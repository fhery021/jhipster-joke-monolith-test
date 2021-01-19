package com.fhery021.joke.monolith.web.rest;

import com.fhery021.joke.monolith.domain.Like;
import com.fhery021.joke.monolith.repository.LikeRepository;
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
 * REST controller for managing {@link com.fhery021.joke.monolith.domain.Like}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LikeResource {

    private final Logger log = LoggerFactory.getLogger(LikeResource.class);

    private static final String ENTITY_NAME = "like";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeRepository likeRepository;

    public LikeResource(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    /**
     * {@code POST  /likes} : Create a new like.
     *
     * @param like the like to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new like, or with status {@code 400 (Bad Request)} if the like has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/likes")
    public ResponseEntity<Like> createLike(@Valid @RequestBody Like like) throws URISyntaxException {
        log.debug("REST request to save Like : {}", like);
        if (like.getId() != null) {
            throw new BadRequestAlertException("A new like cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Like result = likeRepository.save(like);
        return ResponseEntity.created(new URI("/api/likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /likes} : Updates an existing like.
     *
     * @param like the like to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated like,
     * or with status {@code 400 (Bad Request)} if the like is not valid,
     * or with status {@code 500 (Internal Server Error)} if the like couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/likes")
    public ResponseEntity<Like> updateLike(@Valid @RequestBody Like like) throws URISyntaxException {
        log.debug("REST request to update Like : {}", like);
        if (like.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Like result = likeRepository.save(like);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, like.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /likes} : get all the likes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likes in body.
     */
    @GetMapping("/likes")
    public List<Like> getAllLikes() {
        log.debug("REST request to get all Likes");
        return likeRepository.findAll();
    }

    /**
     * {@code GET  /likes/:id} : get the "id" like.
     *
     * @param id the id of the like to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the like, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/likes/{id}")
    public ResponseEntity<Like> getLike(@PathVariable Long id) {
        log.debug("REST request to get Like : {}", id);
        Optional<Like> like = likeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(like);
    }

    /**
     * {@code DELETE  /likes/:id} : delete the "id" like.
     *
     * @param id the id of the like to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/likes/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        log.debug("REST request to delete Like : {}", id);
        likeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
