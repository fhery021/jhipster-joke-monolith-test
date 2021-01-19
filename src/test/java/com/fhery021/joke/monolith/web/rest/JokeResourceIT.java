package com.fhery021.joke.monolith.web.rest;

import com.fhery021.joke.monolith.JokeMonolithTestApp;
import com.fhery021.joke.monolith.domain.Joke;
import com.fhery021.joke.monolith.repository.JokeRepository;
import com.fhery021.joke.monolith.service.JokeService;

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
 * Integration tests for the {@link JokeResource} REST controller.
 */
@SpringBootTest(classes = JokeMonolithTestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JokeResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private JokeService jokeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJokeMockMvc;

    private Joke joke;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Joke createEntity(EntityManager em) {
        Joke joke = new Joke()
            .question(DEFAULT_QUESTION)
            .answer(DEFAULT_ANSWER);
        return joke;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Joke createUpdatedEntity(EntityManager em) {
        Joke joke = new Joke()
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER);
        return joke;
    }

    @BeforeEach
    public void initTest() {
        joke = createEntity(em);
    }

    @Test
    @Transactional
    public void createJoke() throws Exception {
        int databaseSizeBeforeCreate = jokeRepository.findAll().size();
        // Create the Joke
        restJokeMockMvc.perform(post("/api/jokes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(joke)))
            .andExpect(status().isCreated());

        // Validate the Joke in the database
        List<Joke> jokeList = jokeRepository.findAll();
        assertThat(jokeList).hasSize(databaseSizeBeforeCreate + 1);
        Joke testJoke = jokeList.get(jokeList.size() - 1);
        assertThat(testJoke.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testJoke.getAnswer()).isEqualTo(DEFAULT_ANSWER);
    }

    @Test
    @Transactional
    public void createJokeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jokeRepository.findAll().size();

        // Create the Joke with an existing ID
        joke.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJokeMockMvc.perform(post("/api/jokes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(joke)))
            .andExpect(status().isBadRequest());

        // Validate the Joke in the database
        List<Joke> jokeList = jokeRepository.findAll();
        assertThat(jokeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jokeRepository.findAll().size();
        // set the field null
        joke.setQuestion(null);

        // Create the Joke, which fails.


        restJokeMockMvc.perform(post("/api/jokes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(joke)))
            .andExpect(status().isBadRequest());

        List<Joke> jokeList = jokeRepository.findAll();
        assertThat(jokeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = jokeRepository.findAll().size();
        // set the field null
        joke.setAnswer(null);

        // Create the Joke, which fails.


        restJokeMockMvc.perform(post("/api/jokes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(joke)))
            .andExpect(status().isBadRequest());

        List<Joke> jokeList = jokeRepository.findAll();
        assertThat(jokeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJokes() throws Exception {
        // Initialize the database
        jokeRepository.saveAndFlush(joke);

        // Get all the jokeList
        restJokeMockMvc.perform(get("/api/jokes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(joke.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)));
    }
    
    @Test
    @Transactional
    public void getJoke() throws Exception {
        // Initialize the database
        jokeRepository.saveAndFlush(joke);

        // Get the joke
        restJokeMockMvc.perform(get("/api/jokes/{id}", joke.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(joke.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER));
    }
    @Test
    @Transactional
    public void getNonExistingJoke() throws Exception {
        // Get the joke
        restJokeMockMvc.perform(get("/api/jokes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJoke() throws Exception {
        // Initialize the database
        jokeService.save(joke);

        int databaseSizeBeforeUpdate = jokeRepository.findAll().size();

        // Update the joke
        Joke updatedJoke = jokeRepository.findById(joke.getId()).get();
        // Disconnect from session so that the updates on updatedJoke are not directly saved in db
        em.detach(updatedJoke);
        updatedJoke
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER);

        restJokeMockMvc.perform(put("/api/jokes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJoke)))
            .andExpect(status().isOk());

        // Validate the Joke in the database
        List<Joke> jokeList = jokeRepository.findAll();
        assertThat(jokeList).hasSize(databaseSizeBeforeUpdate);
        Joke testJoke = jokeList.get(jokeList.size() - 1);
        assertThat(testJoke.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testJoke.getAnswer()).isEqualTo(UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void updateNonExistingJoke() throws Exception {
        int databaseSizeBeforeUpdate = jokeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJokeMockMvc.perform(put("/api/jokes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(joke)))
            .andExpect(status().isBadRequest());

        // Validate the Joke in the database
        List<Joke> jokeList = jokeRepository.findAll();
        assertThat(jokeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJoke() throws Exception {
        // Initialize the database
        jokeService.save(joke);

        int databaseSizeBeforeDelete = jokeRepository.findAll().size();

        // Delete the joke
        restJokeMockMvc.perform(delete("/api/jokes/{id}", joke.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Joke> jokeList = jokeRepository.findAll();
        assertThat(jokeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
