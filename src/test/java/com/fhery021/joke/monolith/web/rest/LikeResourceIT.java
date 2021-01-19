package com.fhery021.joke.monolith.web.rest;

import com.fhery021.joke.monolith.JokeMonolithTestApp;
import com.fhery021.joke.monolith.domain.Like;
import com.fhery021.joke.monolith.repository.LikeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LikeResource} REST controller.
 */
@SpringBootTest(classes = JokeMonolithTestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LikeResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikeMockMvc;

    private Like like;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createEntity(EntityManager em) {
        Like like = new Like()
            .userId(DEFAULT_USER_ID);
        return like;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createUpdatedEntity(EntityManager em) {
        Like like = new Like()
            .userId(UPDATED_USER_ID);
        return like;
    }

    @BeforeEach
    public void initTest() {
        like = createEntity(em);
    }

    @Test
    @Transactional
    public void createLike() throws Exception {
        int databaseSizeBeforeCreate = likeRepository.findAll().size();
        // Create the Like
        restLikeMockMvc.perform(post("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isCreated());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate + 1);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createLikeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeRepository.findAll().size();

        // Create the Like with an existing ID
        like.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeMockMvc.perform(post("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = likeRepository.findAll().size();
        // set the field null
        like.setUserId(null);

        // Create the Like, which fails.


        restLikeMockMvc.perform(post("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isBadRequest());

        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLikes() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList
        restLikeMockMvc.perform(get("/api/likes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(like.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get the like
        restLikeMockMvc.perform(get("/api/likes/{id}", like.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(like.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingLike() throws Exception {
        // Get the like
        restLikeMockMvc.perform(get("/api/likes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like
        Like updatedLike = likeRepository.findById(like.getId()).get();
        // Disconnect from session so that the updates on updatedLike are not directly saved in db
        em.detach(updatedLike);
        updatedLike
            .userId(UPDATED_USER_ID);

        restLikeMockMvc.perform(put("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLike)))
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeMockMvc.perform(put("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        int databaseSizeBeforeDelete = likeRepository.findAll().size();

        // Delete the like
        restLikeMockMvc.perform(delete("/api/likes/{id}", like.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
